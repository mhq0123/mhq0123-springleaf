package org.mhq0123.springleaf.core.factory;

import com.sun.mail.util.MailSSLSocketFactory;

import java.security.GeneralSecurityException;

/**
 * project: mhq0123-officialwebsite
 * author:  mhq0123
 * date:    2017/1/14.
 * desc:    ssl认证 全部信任
 */
public class TrustAllMailSSLSocketFactory extends MailSSLSocketFactory{

    public TrustAllMailSSLSocketFactory() throws GeneralSecurityException {
        super();
        this.setTrustAllHosts(true);
    }
}
