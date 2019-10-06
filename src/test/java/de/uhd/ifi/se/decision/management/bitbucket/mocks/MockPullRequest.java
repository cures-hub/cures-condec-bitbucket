package de.uhd.ifi.se.decision.management.bitbucket.mocks;

import java.util.Date;
import java.util.Set;

import com.atlassian.bitbucket.comment.CommentableVisitor;
import com.atlassian.bitbucket.property.PropertyMap;
import com.atlassian.bitbucket.pull.PullRequest;
import com.atlassian.bitbucket.pull.PullRequestParticipant;
import com.atlassian.bitbucket.pull.PullRequestRef;
import com.atlassian.bitbucket.pull.PullRequestState;
import com.atlassian.bitbucket.watcher.WatchableVisitor;
import com.atlassian.bitbucket.watcher.Watcher;

/**
 * Mock class for pull requests.
 */
public class MockPullRequest implements PullRequest {
	
	@Override
	public PullRequestRef getFromRef() {
		return new MockPullRequestRef();
	}
	
	@Override
	public String getTitle() {
		return "ConDec-1: Create a great plugin";
	}

	@Override
	public PropertyMap getProperties() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T accept(CommentableVisitor<T> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T accept(WatchableVisitor<T> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Watcher> getWatchers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PullRequestParticipant getAuthor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Date getClosedDate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Date getCreatedDate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Set<PullRequestParticipant> getParticipants() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<PullRequestParticipant> getReviewers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PullRequestState getState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PullRequestRef getToRef() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Date getUpdatedDate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getVersion() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isClosed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCrossRepository() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isLocked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isOpen() {
		// TODO Auto-generated method stub
		return false;
	}
	
}
