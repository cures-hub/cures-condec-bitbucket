package de.uhd.ifi.se.decision.management.bitbucket.mocks;

import com.atlassian.bitbucket.label.LabelableVisitor;
import com.atlassian.bitbucket.project.Project;
import com.atlassian.bitbucket.repository.Repository;
import com.atlassian.bitbucket.watcher.WatchableVisitor;

/**
 * Mock class for a git repository.
 */
public class MockRepository implements Repository {

	@Override
	public String getHierarchyId() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isRemote() {
		return true;
	}

	public boolean isReadOnly() {
		return true;
	}

	public boolean isOffline() {
		return true;
	}

	public boolean isLocal() {
		return false;
	}

	public boolean isArchived() {
		return false;
	}

	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Repository getOrigin() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Project getProject() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getScmId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSlug() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public State getState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getStatusMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isFork() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isForkable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isPublic() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public <T> T accept(LabelableVisitor<T> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T accept(WatchableVisitor<T> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

}
