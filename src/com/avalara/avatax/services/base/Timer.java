package com.avalara.avatax.services.base;

import com.avalara.avatax.services.tax.GetTaxResult;
import com.avalara.avatax.services.tax.TaxSvcSoap;
import com.avalara.avatax.services.tax.TaxSvcSoapStub;
import com.avalara.avatax.services.address.ValidateResult;
import com.avalara.avatax.services.address.AddressSvcSoapStub;

import java.rmi.RemoteException;

/**
 * Created by IntelliJ IDEA.
 * User: amit.raval
 * Date: Nov 6, 2007
 * Time: 12:00:17 PM
 * To change this template use File | Settings | File Templates.
 * Time class is responsible to send ClientMetrics to the server for time taken to server a request on client side
 */
public class Timer
{
    private long startTime = 0;
    private long endTime = 0;
    long totalTimeTaken = 0;
    private boolean started = false;
    public Timer()
    {

    }

    /**
     * Starts the clock
     */
    public void start()
    {
        startTime = System.currentTimeMillis();
        started = true;
    }

    /**
     * Stops the clock and find out the difference
     * @param svc  Expected TaxSvcSoapStub object or AddressSvcSoapStub
     * @param result Expected com.avalara.avatax.services.tax.GetTaxResult or com.avalara.avatax.services.address.ValidateResult
     */
    public void stop(Object svc,Object result)
    {
        if(started)
        {
            endTime = System.currentTimeMillis();
            totalTimeTaken +=  endTime - startTime;
            if(result.getClass().getName().equals("com.avalara.avatax.services.tax.GetTaxResult"))
            {
                GetTaxResult taxResult = (com.avalara.avatax.services.tax.GetTaxResult)result;
                TaxSvcSoapStub taxSvc = (TaxSvcSoapStub) svc;
                try
                {
                    if(taxResult.getMessages().hasClientMetricMessage())
                    {
                        taxSvc.ping(Utility.BuildAuditMetrics("",taxResult.getTransactionId().toString(),totalTimeTaken));
                    }
                }
                catch (Exception e)
                {
                    //Ignore Exception while sending ClientMetrics
                    //e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
            else if(result.getClass().getName().equals("com.avalara.avatax.services.address.ValidateResult"))
            {
                ValidateResult addressResult = (com.avalara.avatax.services.address.ValidateResult)result;
                AddressSvcSoapStub addressSvc = (AddressSvcSoapStub) svc;
                try
                {
                    addressSvc.ping(Utility.BuildAuditMetrics("",addressResult.getTransactionId().toString(),totalTimeTaken));
                }
                catch (Exception e)
                {
                    //Ignore Exception while sending ClientMetrics
                    //e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
            else
            {
                //Ignore Do not send clientmetrics information for any other operations                
            }
        }
    }
    public void reset()
    {
        startTime = 0;
        endTime = 0;
        totalTimeTaken = 0;
    }
    public long getTotal()
    {
        return totalTimeTaken;
    }
}
