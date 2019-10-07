package de.uhd.ifi.se.decision.management.bitbucket.mocks;

import com.atlassian.bitbucket.pull.PullRequestRef;
import com.atlassian.bitbucket.repository.RefType;
import com.atlassian.bitbucket.repository.Repository;

/**
 * Mock class for the branch linked to the pull request.
 */
public class MockPullRequestRef implements PullRequestRef {

	@Override
	public String getDisplayId() {
		return "CONDEC-1.create.a.great.plugin";
	}

	@Override
	public Repository getRepository() {
		return new MockRepository();
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RefType getType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getLatestCommit() {
		return "123";
	}

}
