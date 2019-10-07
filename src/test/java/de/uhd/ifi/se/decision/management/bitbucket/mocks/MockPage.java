package de.uhd.ifi.se.decision.management.bitbucket.mocks;

import java.util.ArrayList;
import java.util.SortedMap;
import java.util.function.Function;

import com.atlassian.bitbucket.commit.Commit;
import com.atlassian.bitbucket.util.Page;
import com.atlassian.bitbucket.util.PageRequest;

/**
 * Mock class for pagination.
 */
public class MockPage implements Page<Commit> {
	
	@Override
	public Iterable<Commit> getValues() {
		return new ArrayList<Commit>();
	}

	@Override
	public boolean getIsLastPage() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getLimit() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public PageRequest getNextPageRequest() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public SortedMap getOrdinalIndexedValues() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getStart() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Page transform(Function arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
