package de.uhd.ifi.se.decision.management.bitbucket.mocks;

import com.atlassian.applinks.api.ApplicationId;
import com.atlassian.applinks.api.ApplicationLink;
import com.atlassian.applinks.api.ApplicationLinkService;
import com.atlassian.applinks.api.ApplicationType;
import com.atlassian.applinks.api.TypeNotInstalledException;

public class MockApplicationLinkService implements ApplicationLinkService {

	@Override
	public ApplicationLink getApplicationLink(ApplicationId arg0) throws TypeNotInstalledException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<ApplicationLink> getApplicationLinks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<ApplicationLink> getApplicationLinks(Class<? extends ApplicationType> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ApplicationLink getPrimaryApplicationLink(Class<? extends ApplicationType> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
