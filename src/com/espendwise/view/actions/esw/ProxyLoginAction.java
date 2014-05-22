package com.espendwise.view.actions.esw;

public class ProxyLoginAction extends SecurityAction{

	/* (non-Javadoc)
     * For login as we do not want to go to SSL.  In particular it will break
     * if we go in and then redirect back out as certain request variables will
     * get lost.
     *
     * @see com.cleanwise.view.actions.ActionSuper#isRequiresConfidentialConnection()
     */
	 @Override
    protected boolean isRequiresConfidentialConnection(){
        return false;
    }
}
