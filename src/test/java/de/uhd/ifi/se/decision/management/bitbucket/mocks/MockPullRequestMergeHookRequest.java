package de.uhd.ifi.se.decision.management.bitbucket.mocks;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import com.atlassian.bitbucket.hook.ScmHookDetails;
import com.atlassian.bitbucket.hook.repository.PullRequestMergeHookRequest;
import com.atlassian.bitbucket.hook.repository.RepositoryHookTrigger;
import com.atlassian.bitbucket.pull.PullRequest;
import com.atlassian.bitbucket.pull.PullRequestRef;
import com.atlassian.bitbucket.repository.RefChange;
import com.atlassian.bitbucket.repository.Repository;

public class MockPullRequestMergeHookRequest implements PullRequestMergeHookRequest {

	@Override
	public PullRequest getPullRequest() {
		return new MockPullRequest();
	}

	@Override
	public Optional<String> getMergeHash() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<String> getMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<String> getStrategyId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isCrossRepository() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Map<String, Object> getContext() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<RefChange> getRefChanges() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Repository getRepository() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<ScmHookDetails> getScmHookDetails() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RepositoryHookTrigger getTrigger() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isDryRun() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public PullRequestRef getFromRef() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PullRequestRef getToRef() {
		// TODO Auto-generated method stub
		return null;
	}

}
