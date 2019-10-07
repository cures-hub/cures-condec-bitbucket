package de.uhd.ifi.se.decision.management.bitbucket.mocks;

import com.atlassian.bitbucket.project.Project;
import com.atlassian.bitbucket.repository.Repository;

/**
 * Mock class for a git repository.
 */
public class MockRepository implements Repository {
	
	public String getDescription() {
		return "";
	}

	@Override
	public String getHierarchyId() {
		// TODO Auto-generated method stub
		return null;
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

}
