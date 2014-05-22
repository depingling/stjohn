package com.avalara.avatax.services.base;

import org.apache.axis.message.SOAPHeaderElement;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import java.net.InetAddress;
import java.net.UnknownHostException;


/**
 * The request profile provides information about the calling
 * application and may be used for customized request processing.
 * <pre>
 * <b>Example:</b>
 * EngineConfiguration config = new FileProvider("avatax4j.wsdd");
 *
 * AddressSvcLocator svcLoc = new AddressSvcLocator(config);
 * AddressSvcSoap svc = svcLoc.getAddressSvcSoap(new URL("http://www.avalara.com/services/"));
 *
 * // Set the profile
 * Profile profile = new Profile();
 * profile.setClient("AddressSvcTest,4.0.0.0");
 * svc.setProfile(profile);
 *
 * // Set security
 * Security security = new Security();
 * security.setAccount("account");
 * security.setLicense("license number");
 * svc.setSecurity(security);
 *
 * PingResult result = svc.ping("");
 * </pre>
 *
 * @author brianh
 * Copyright (c) 2005, Avalara.  All rights reserved.
 */
public class Profile
{
    //private java.lang.String name;
    private String name;
    private String client;

    private static final String adapter = "avatax4j,5.4.0.0";

    private String machine;

    public static final java.lang.String PROFILE_ELEMENT = "Profile";
    public static final java.lang.String NAMESPACE = "http://avatax.avalara.com/services";

    /**
     * Initializes a new instance of the class.
     */
    public Profile()
    {
        name = "5.3.0.1";
        client = "";
        try
        {
            machine = InetAddress.getLocalHost().getHostName();
        }
        catch (UnknownHostException e)
        {
            machine = "UNKNOWN";
        }

    }

    // * Provides access to the request profile data. Should be set prior to making a request.

    /**
     * Reference to a named profile. Designed to allow shared and custom profile.  Reserved for future use.
     *
     * @param value String value to use for this profile.
     */
    public void setName(String value)
    {
        name = value;
    }

    /**
     * Reference to a named profile. Designed to allow shared and custom profile.  Reserved for future use.
     *
     * @return String value to use for this profile.
     */
    public String getName()
    {
        return name;
    }

    /**
     * Client application name and version (and plugin name and version, if applicable).
     *
     * This should be of the form: Application,ApplicationVersion[,Plugin,PluginVersion].  The plugin information is optional
     * in the case of a direct or custom application integration.
     * <table>
     * <tr>
     * <th>Segment</th><th>Description</th>
     * </tr>
     * <tr>
     * <td>Application</td><td>Name of the application (e.g. SuperERP)</td>
     * </tr>
     * <tr>
     * <td>ApplicationVersion</td><td>Version of the application (e.g. 2.5.1.23)</td>
     * </tr>
     * <tr>
     * <td>Plugin</td><td>Name of the plugin, if applicable (e.g. DuperPlugin)</td>
     * </tr>
     * <tr>
     * <td>PluginVersion</td><td>Version of the plugin, if applicable (e.g. 2.5.0.1)</td>
     * </tr>
     * </table>
     * <br><b>Example:</b>
     * <code>
     * <br>[C#]
     * <br>svc.Profile.Client = "SuperERP,2.5.1.23,DuperPlugin,2.5.0.1"
     * </code>
     *
     * @param value String containing client information
     */
    public void setClient(String value)
    {
        client = value;
    }

    /**
     * Client application name and version (and plugin name and version, if applicable).
     *
     * This should be of the form: Application,ApplicationVersion[,Plugin,PluginVersion].  The plugin information is optional
     * in the case of a direct or custom application integration.
     * <table>
     * <tr>
     * <th>Segment</th><th>Description</th>
     * </tr>
     * <tr>
     * <td>Application</td><td>Name of the application (e.g. SuperERP)</td>
     * </tr>
     * <tr>
     * <td>ApplicationVersion</td><td>Version of the application (e.g. 2.5.1.23)</td>
     * </tr>
     * <tr>
     * <td>Plugin</td><td>Name of the plugin, if applicable (e.g. DuperPlugin)</td>
     * </tr>
     * <tr>
     * <td>PluginVersion</td><td>Version of the plugin, if applicable (e.g. 2.5.0.1)</td>
     * </tr>
     * </table>
     * <br><b>Example:</b>
     * <code>
     * <br>[C#]
     * <br>svc.Profile.Client = "SuperERP,2.5.1.23,DuperPlugin,2.5.0.1"
     * </code>
     *
     * @return String containing client information
     */
    public String getClient()
    {
        return client;
    }

    /**
     * Returns the {@link SOAPHeaderElement} for this Profile object;
     * this method is used internally by the adapter
     * and not intended to be used by external implementation code.
     *
     * @return a {@link SOAPHeaderElement} containing information about the Profile object.
     * @throws SOAPException
     */
    public SOAPHeaderElement getSOAPHeaderElement() throws SOAPException
    {
        SOAPHeaderElement profileHeader = new org.apache.axis.message.SOAPHeaderElement(NAMESPACE, PROFILE_ELEMENT);
        SOAPElement nameElement = profileHeader.addChildElement("Name");
        nameElement.setValue(name);
        SOAPElement clientElement = profileHeader.addChildElement("Client");
        clientElement.setValue(client);
        SOAPElement adapterElement = profileHeader.addChildElement("Adapter");
        adapterElement.setValue(adapter);
        SOAPElement machineElement = profileHeader.addChildElement("Machine");
        machineElement.setValue(machine);

        return profileHeader;
    }
}
