package de.uhd.ifi.se.decision.management.bitbucket.mocks;

import java.util.Collection;
import java.util.Date;

import com.atlassian.bitbucket.commit.Commit;
import com.atlassian.bitbucket.commit.MinimalCommit;
import com.atlassian.bitbucket.property.PropertyMap;
import com.atlassian.bitbucket.repository.Repository;
import com.atlassian.bitbucket.user.Person;

public class MockCommit implements Commit {

	@Override
	public PropertyMap getProperties() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDisplayId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Person getAuthor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Date getAuthorTimestamp() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getMessage() {
		return "ConDec-1: Initial commit";
	}

	@Override
	public Collection<MinimalCommit> getParents() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Repository getRepository() {
		// TODO Auto-generated method stub
		return null;
	}

}
