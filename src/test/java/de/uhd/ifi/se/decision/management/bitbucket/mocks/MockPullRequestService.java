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
import com.atlassian.bitbucket.pull.PullRequestUpdateRequest;
import com.atlassian.bitbucket.pull.SetPullRequestMergeConfigRequest;
import com.atlassian.bitbucket.user.ApplicationUser;
import com.atlassian.bitbucket.util.Page;
import com.atlassian.bitbucket.util.PageRequest;

/**
 * Mock class for the PullRequestService.
 */
public class MockPullRequestService implements PullRequestService {

	@Override
	public PullRequestParticipant addReviewer(int repositoryId, long pullRequestId, String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean canDelete(int repositoryId, long pullRequestId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public PullRequestMergeability canMerge(int repositoryId, long pullRequestId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long count(PullRequestSearchRequest request) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long countByCommit(PullRequestCommitSearchRequest request) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long countCommits(int repositoryId, long pullRequestId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public PullRequest create(PullRequestCreateRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PullRequest decline(PullRequestDeclineRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(PullRequestDeleteRequest request) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteMergeConfig(DeletePullRequestMergeConfigRequest request) {
		// TODO Auto-generated method stub

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
	public Page<PullRequestActivity> getActivities(int repositoryId, long pullRequestId, PageRequest pageRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<PullRequestActivity> getActivitiesById(int repositoryId, long pullRequestId, Set<Long> activityIds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PullRequestActivityPage<PullRequestActivity> getActivitiesStartingAt(int repositoryId, long pullRequestId,
			PullRequestEntityType fromType, long fromId, PageRequest pageRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PullRequest getById(int arg0, long arg1) {
		return new MockPullRequest();
	}

	@Override
	public Page<Commit> getCommits(PullRequestCommitsRequest request, PageRequest pageRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	public Page<Commit> getCommits(int repositoryId, long pullRequestId, PageRequest pageRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PullRequestMergeConfig getMergeConfig(GetPullRequestMergeConfigRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<PullRequestParticipant> getParticipants(int repositoryId, long pullRequestId, PageRequest pageRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<CommentThread> getReviewThreads(int repositoryId, long pullRequestId, PageRequest pageRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PullRequest merge(PullRequestMergeRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeReviewer(int repositoryId, long pullRequestId, String username) {
		// TODO Auto-generated method stub

	}

	@Override
	public PullRequest reopen(int repositoryId, long pullRequestId, int version) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<PullRequest> search(PullRequestSearchRequest request, PageRequest pageRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<PullRequestActivity> searchActivities(PullRequestActivitySearchRequest request,
			PageRequest pageRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<PullRequest> searchByCommit(PullRequestCommitSearchRequest request, PageRequest pageRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<ApplicationUser> searchUsers(PullRequestParticipantSearchRequest searchRequest,
			PageRequest pageRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setMergeConfig(SetPullRequestMergeConfigRequest request) {
		// TODO Auto-generated method stub

	}

	@Override
	public PullRequestParticipant setReviewerStatus(int repositoryId, long pullRequestId,
			PullRequestParticipantStatus status) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void streamChanges(PullRequestChangesRequest request, ChangeCallback callback) {
		// TODO Auto-generated method stub

	}

	@Override
	public void streamCommits(PullRequestCommitsRequest request, CommitCallback callback) {
		// TODO Auto-generated method stub

	}

	public void streamCommits(int repositoryId, long pullRequestId, CommitCallback callback) {
		// TODO Auto-generated method stub

	}

	@Override
	public void streamDiff(PullRequestDiffRequest request, DiffContentCallback callback) {
		// TODO Auto-generated method stub

	}

	@Override
	public void streamDiff(PullRequestDiffRequest request, TypeAwareOutputSupplier outputSupplier) {
		// TODO Auto-generated method stub

	}

	@Override
	public PullRequest update(PullRequestUpdateRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

}
