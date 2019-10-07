package de.uhd.ifi.se.decision.management.bitbucket.mocks;

import java.util.Optional;

import com.atlassian.bitbucket.commit.Changeset;
import com.atlassian.bitbucket.commit.ChangesetsRequest;
import com.atlassian.bitbucket.commit.Commit;
import com.atlassian.bitbucket.commit.CommitCallback;
import com.atlassian.bitbucket.commit.CommitDiscussion;
import com.atlassian.bitbucket.commit.CommitDiscussionRequest;
import com.atlassian.bitbucket.commit.CommitRequest;
import com.atlassian.bitbucket.commit.CommitService;
import com.atlassian.bitbucket.commit.CommitsBetweenRequest;
import com.atlassian.bitbucket.commit.CommitsRequest;
import com.atlassian.bitbucket.commit.CommonAncestorRequest;
import com.atlassian.bitbucket.commit.LastModifiedCallback;
import com.atlassian.bitbucket.commit.LastModifiedRequest;
import com.atlassian.bitbucket.commit.MinimalCommit;
import com.atlassian.bitbucket.commit.graph.TraversalCallback;
import com.atlassian.bitbucket.commit.graph.TraversalRequest;
import com.atlassian.bitbucket.content.Change;
import com.atlassian.bitbucket.content.ChangeCallback;
import com.atlassian.bitbucket.content.ChangesRequest;
import com.atlassian.bitbucket.content.DiffContentCallback;
import com.atlassian.bitbucket.content.DiffRequest;
import com.atlassian.bitbucket.repository.Repository;
import com.atlassian.bitbucket.util.Page;
import com.atlassian.bitbucket.util.PageRequest;
import com.atlassian.bitbucket.watcher.Watcher;

public class MockCommitService implements CommitService {

	@Override
	public Page<Commit> getCommitsBetween(CommitsBetweenRequest arg0, PageRequest arg1) {
		return new MockPage();
	}

	@Override
	public Page<Change> getChanges(ChangesRequest arg0, PageRequest arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Changeset> getChangesets(ChangesetsRequest arg0, PageRequest arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Commit getCommit(CommitRequest arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Commit> getCommits(CommitsRequest arg0, PageRequest arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<MinimalCommit> getCommonAncestor(CommonAncestorRequest arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CommitDiscussion getDiscussion(CommitDiscussionRequest arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void streamChanges(ChangesRequest arg0, ChangeCallback arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void streamCommits(CommitsRequest arg0, CommitCallback arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void streamCommitsBetween(CommitsBetweenRequest arg0, CommitCallback arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void streamDiff(DiffRequest arg0, DiffContentCallback arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void streamLastModified(LastModifiedRequest arg0, LastModifiedCallback arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void traverse(TraversalRequest arg0, TraversalCallback arg1) {
		// TODO Auto-generated method stub

	}

	public boolean unwatch(Repository arg0, String arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	public Watcher watch(Repository arg0, String arg1) {
		// TODO Auto-generated method stub
		return null;
	}

}
