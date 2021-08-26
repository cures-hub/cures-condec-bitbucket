package de.uhd.ifi.se.decision.management.bitbucket.mocks;

import java.util.Set;

import com.atlassian.bitbucket.comment.CommentThread;
import com.atlassian.bitbucket.commit.Commit;
import com.atlassian.bitbucket.commit.CommitCallback;
import com.atlassian.bitbucket.content.ChangeCallback;
import com.atlassian.bitbucket.content.DiffContentCallback;
import com.atlassian.bitbucket.io.TypeAwareOutputSupplier;
import com.atlassian.bitbucket.pull.DeletePullRequestMergeConfigRequest;
import com.atlassian.bitbucket.pull.GetPullRequestMergeConfigRequest;
import com.atlassian.bitbucket.pull.PullRequest;
import com.atlassian.bitbucket.pull.PullRequestActivity;
import com.atlassian.bitbucket.pull.PullRequestActivityPage;
import com.atlassian.bitbucket.pull.PullRequestActivitySearchRequest;
import com.atlassian.bitbucket.pull.PullRequestChangesRequest;
import com.atlassian.bitbucket.pull.PullRequestCommitSearchRequest;
import com.atlassian.bitbucket.pull.PullRequestCommitsRequest;
import com.atlassian.bitbucket.pull.PullRequestCreateRequest;
import com.atlassian.bitbucket.pull.PullRequestDeclineRequest;
import com.atlassian.bitbucket.pull.PullRequestDeleteRequest;
import com.atlassian.bitbucket.pull.PullRequestDiffRequest;
import com.atlassian.bitbucket.pull.PullRequestDiscardReviewRequest;
import com.atlassian.bitbucket.pull.PullRequestEntityType;
import com.atlassian.bitbucket.pull.PullRequestFinishReviewRequest;
import com.atlassian.bitbucket.pull.PullRequestMergeConfig;
import com.atlassian.bitbucket.pull.PullRequestMergeRequest;
import com.atlassian.bitbucket.pull.PullRequestMergeability;
import com.atlassian.bitbucket.pull.PullRequestParticipant;
import com.atlassian.bitbucket.pull.PullRequestParticipantSearchRequest;
import com.atlassian.bitbucket.pull.PullRequestParticipantStatus;
import com.atlassian.bitbucket.pull.PullRequestSearchRequest;
import com.atlassian.bitbucket.pull.PullRequestService;
import com.atlassian.bitbucket.pull.PullRequestTaskSearchRequest;
import com.atlassian.bitbucket.pull.PullRequestUpdateRequest;
import com.atlassian.bitbucket.pull.SetPullRequestMergeConfigRequest;
import com.atlassian.bitbucket.task.Task;
import com.atlassian.bitbucket.task.TaskCount;
import com.atlassian.bitbucket.user.ApplicationUser;
import com.atlassian.bitbucket.util.Page;
import com.atlassian.bitbucket.util.PageRequest;

/**
 * Mock class for the PullRequestService.
 */
public class MockPullRequestService implements PullRequestService {

	@Override
	public PullRequestParticipant addReviewer(int arg0, long arg1, String arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean canDelete(int arg0, long arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public PullRequestMergeability canMerge(int arg0, long arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long count(PullRequestSearchRequest arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long countByCommit(PullRequestCommitSearchRequest arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long countCommits(int arg0, long arg1) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public TaskCount countTasks(PullRequestTaskSearchRequest arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PullRequest create(PullRequestCreateRequest arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PullRequest decline(PullRequestDeclineRequest arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(PullRequestDeleteRequest arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteMergeConfig(DeletePullRequestMergeConfigRequest arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public Page<PullRequestActivity> getActivities(int arg0, long arg1, PageRequest arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<PullRequestActivity> getActivitiesById(int arg0, long arg1, Set<Long> arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PullRequestActivityPage<PullRequestActivity> getActivitiesStartingAt(int arg0, long arg1,
			PullRequestEntityType arg2, long arg3, PageRequest arg4) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PullRequest getById(int arg0, long arg1) {
		return new MockPullRequest();
	}

	@Override
	public Page<Commit> getCommits(PullRequestCommitsRequest arg0, PageRequest arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Commit> getCommits(int arg0, long arg1, PageRequest arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PullRequestMergeConfig getMergeConfig(GetPullRequestMergeConfigRequest arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<PullRequestParticipant> getParticipants(int arg0, long arg1, PageRequest arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PullRequest merge(PullRequestMergeRequest arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeReviewer(int arg0, long arg1, String arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public PullRequest reopen(int arg0, long arg1, int arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<PullRequest> search(PullRequestSearchRequest arg0, PageRequest arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<PullRequestActivity> searchActivities(PullRequestActivitySearchRequest arg0, PageRequest arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<PullRequest> searchByCommit(PullRequestCommitSearchRequest arg0, PageRequest arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Task> searchTasks(PullRequestTaskSearchRequest arg0, PageRequest arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<ApplicationUser> searchUsers(PullRequestParticipantSearchRequest arg0, PageRequest arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setMergeConfig(SetPullRequestMergeConfigRequest arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public PullRequestParticipant setReviewerStatus(int arg0, long arg1, PullRequestParticipantStatus arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void streamChanges(PullRequestChangesRequest arg0, ChangeCallback arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void streamCommits(PullRequestCommitsRequest arg0, CommitCallback arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void streamCommits(int arg0, long arg1, CommitCallback arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void streamDiff(PullRequestDiffRequest arg0, DiffContentCallback arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public PullRequest update(PullRequestUpdateRequest arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void discardReview(PullRequestDiscardReviewRequest request) {
		// TODO Auto-generated method stub

	}

	@Override
	public int finishReview(PullRequestFinishReviewRequest request) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Page<CommentThread> getReviewThreads(int repositoryId, long pullRequestId, PageRequest pageRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void streamDiff(PullRequestDiffRequest request, TypeAwareOutputSupplier outputSupplier) {
		// TODO Auto-generated method stub

	}

}
