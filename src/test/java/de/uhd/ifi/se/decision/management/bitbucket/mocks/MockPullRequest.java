package de.uhd.ifi.se.decision.management.bitbucket.mocks;

import java.util.Date;
import java.util.HashSet;
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
		return null;
	}

	@Override
	public <T> T accept(CommentableVisitor<T> arg0) {
		return null;
	}

	@Override
	public <T> T accept(WatchableVisitor<T> arg0) {
		return null;
	}

	@Override
	public PullRequestParticipant getAuthor() {
		return null;
	}

	@Override
	public Date getClosedDate() {
		return null;
	}

	@Override
	public Date getCreatedDate() {
		return null;
	}

	@Override
	public String getDescription() {
		return null;
	}

	@Override
	public long getId() {
		return 0;
	}

	@Override
	public Set<PullRequestParticipant> getParticipants() {
		return null;
	}

	@Override
	public Set<PullRequestParticipant> getReviewers() {
		return null;
	}

	@Override
	public PullRequestState getState() {
		return null;
	}

	@Override
	public PullRequestRef getToRef() {
		return null;
	}

	@Override
	public Date getUpdatedDate() {
		return null;
	}

	@Override
	public int getVersion() {
		return 0;
	}

	@Override
	public boolean isClosed() {
		return false;
	}

	@Override
	public boolean isCrossRepository() {
		return false;
	}

	@Override
	public boolean isLocked() {
		return false;
	}

	@Override
	public boolean isOpen() {
		return false;
	}

	public Set<Watcher> getWatchers() {
		return new HashSet<Watcher>();
	}
}
