package com.smartbidderbatch;

import com.smartbidderbatch.domain.*;
import com.smartbidderbatch.repository.ProjectBidRepository;
import com.smartbidderbatch.repository.ProjectRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;

@Component
@AllArgsConstructor
@Slf4j
public class ProjectBidSelectProcessor implements ItemProcessor<Project,Void> {

    private final ProjectRepository projectRepository;
    private final ProjectBidRepository projectBidRepository;

    @Override
    public Void process(Project project) throws Exception {
        log.info("Processing project : "+project);
        List<ProjectBid> projectBidList = projectBidRepository.findProjectBidByProjectId(project.getId());
        ProjectBid minBid = projectBidList.stream()
                .min(new BidComparator(project.getExpectedNoOfHours())).orElse(null);
        log.info("Min Bid: "+minBid);
        project.setAssignedBidId(minBid.getId());
        project.setStatus(ProjectStatus.ASSIGNED);
        project.setLastModifiedBy("Batch");
        project.setLastModifiedDate(Instant.now());
        projectRepository.save(project);
        updateBidStatus(minBid,BidStatus.ACCEPTED);
        projectBidList.stream().filter(bid -> !bid.equals(minBid))
            .forEach( bid -> updateBidStatus(bid,BidStatus.DECLINED));
        return null;
    }

    private void updateBidStatus(ProjectBid bid,BidStatus status) {
        bid.setBidStatus(status);
        bid.setLastModifiedBy("Batch");
        bid.setLastModifiedDate(Instant.now());
        projectBidRepository.save(bid);
    }


    class BidComparator implements Comparator<ProjectBid>{
        BidComparator(int noOfHours){
            this.noOfHours=noOfHours;
        }
        private int noOfHours;

        @Override
        public int compare(ProjectBid bid1, ProjectBid bid2) {
            double bidAmt1 = bid1.getBidType()== BidType.FIXED?bid1.getBidAmount():bid1.getBidAmount()*noOfHours;
            double bidAmt2 = bid1.getBidType()== BidType.FIXED?bid1.getBidAmount():bid1.getBidAmount()*noOfHours;
            return (int)(bidAmt1-bidAmt2);
        }
    }

}
