package com.cleanwise.compass;

import org.apache.log4j.Logger;
import org.compass.gps.*;
import org.compass.gps.device.AbstractMirrorGpsDeviceWrapper;

public class ScheduledMirroringGpsDevice extends AbstractMirrorGpsDeviceWrapper implements ActiveMirrorGpsDevice {
  private static final Logger log = Logger.getLogger(ScheduledMirroringGpsDevice.class);
  private ActiveMirrorGpsDevice gpsDevice;
  private MirroringGpsDeviceThread thread;
  private boolean daemon = true;
  private long period = 10000;

  public ScheduledMirroringGpsDevice() {
  }

  public ScheduledMirroringGpsDevice(ActiveMirrorGpsDevice gpsDevice) {
    this.gpsDevice = gpsDevice;
    setGpsDevice(gpsDevice);
  }

  public void setGpsDevice(CompassGpsDevice gpsDevice) {
    if (!(gpsDevice instanceof ActiveMirrorGpsDevice)) {
      throw new IllegalArgumentException("The device must implement the ActiveMirrorGpsDevice interface");
    }
    this.gpsDevice = (ActiveMirrorGpsDevice) gpsDevice;
    super.setGpsDevice(gpsDevice);
  }

  /**
   * Starts the scheduled timer.
   */
  public synchronized void start() throws CompassGpsException {
    if (isRunning()) {
      throw new IllegalStateException("{" + getName() + "} Scheduled mirror device is already running");
    }
    if (log.isInfoEnabled()) {
      log.info("{" + getName() + "} Starting scheduled mirror device with period [" + period + "ms] daemon [" + daemon + "]");
    }
    this.gpsDevice.start();
    thread = new MirroringGpsDeviceThread(period);
    thread.setName("Compass Mirror Gps Device [" + getName() + "]");
    thread.start();
  }

  /**
   * Stops the scheduled timer.
   */
  public synchronized void stop() throws CompassGpsException {
    if (!isRunning()) {
      throw new IllegalStateException("{" + getName() + "} Scheduled mirror device is already running");
    }
    if (log.isInfoEnabled()) {
      log.info("{" + getName() + "} Stopping scheduled mirror device");
    }
    thread.cancel();
    thread = null;
    this.gpsDevice.stop();
  }

  /**
   * Returns the wrapped active mirror gps device.
   */
  public ActiveMirrorGpsDevice getWrappedGpsDevice() {
    return gpsDevice;
  }

  /**
   * Sets the wrapped gps device.
   */
  public void setWrappedGpsDevice(ActiveMirrorGpsDevice gpsDevice) {
    this.gpsDevice = gpsDevice;
    setGpsDevice(gpsDevice);
  }

  /**
   * Performs the actual mirror operation, delegating the action to the
   * wrapped gps device.
   */
  public void performMirroring() throws CompassGpsException {
    checkDeviceSet();
    log.info("performMirroring(). Mirroring is started. this.gpsDevice.isPerformingIndexOperation() = " + this.gpsDevice.isPerformingIndexOperation());
    this.gpsDevice.performMirroring();
    log.info("performMirroring(). Mirroring is finished.");
  }

  /**
   * If the scheduled timer whould work as a daemon thread or not.
   */
  public boolean isDaemon() {
    return daemon;
  }

  /**
   * Sets if the scheduled timer would work as a daemon thread or not.
   */
  public void setDaemon(boolean daemon) {
    this.daemon = daemon;
  }

  /**
   * The period of the scheduled service in milli-seconds.
   */
  public long getPeriod() {
    return period;
  }

  /**
   * Sets the period of the scheduled service in milli-seconds.
   *
   * @param period
   */
  public void setPeriod(long period) {
    this.period = period;
  }

  private class MirroringGpsDeviceThread extends Thread {

    private long period;

    private boolean canceled;

    public MirroringGpsDeviceThread(long period) {
      this.period = period;
    }


    public void run() {
      while (!Thread.interrupted() || !canceled) {
        try {
          Thread.sleep(period);
        } catch (InterruptedException e) {
          break;
        }
        try {
          if (!"disable".equals(System.getProperty("compass.mirroring"))) {
            log.info("run(). Mirroring is started.");
            gpsDevice.performMirroring();
            log.info("run(). Mirroring is finished.");
          } else {
            log.info("run(). Mirroring is disabled.");
          }
        } catch (Exception e) {
          log.warn("Failed to perform gps device mirroring", e);
        }
      }
    }

    public void cancel() {
      this.canceled = true;
      this.interrupt();
    }
  }
}
