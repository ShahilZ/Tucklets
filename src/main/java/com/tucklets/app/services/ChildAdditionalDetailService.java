package com.tucklets.app.services;

import com.tucklets.app.configs.AwsConfig;
import com.tucklets.app.db.repositories.ChildAdditionalDetailRepository;
import com.tucklets.app.entities.ChildAdditionalDetail;
import com.tucklets.app.utils.S3Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ChildAdditionalDetailService {

    @Autowired
    ChildAdditionalDetailRepository childAdditionalDetailRepository;

    @Autowired
    AwsConfig awsConfig;

    /**
     * Method that creates a new ChildAdditionalDetail and sets the most recently used image to be archived.
     */
    public void addImageForChild(String filename, long childId) {

        // Fetch most recent additional detail object; null if none exists.
        ChildAdditionalDetail previousChildAdditionalDetail =
            childAdditionalDetailRepository.fetchChildAdditionalDetailByChildId(childId).orElse(null);

        if (previousChildAdditionalDetail != null) {
            previousChildAdditionalDetail.setArchivedDate(new Date());
            childAdditionalDetailRepository.save(previousChildAdditionalDetail);
        }

        ChildAdditionalDetail childAdditionalDetail = new ChildAdditionalDetail();
        childAdditionalDetail.setChildId(childId);
        childAdditionalDetail.setImageLocation(S3Utils.computeS3Key(filename, awsConfig.getS3ImagesBucketUrl()));
        childAdditionalDetailRepository.save(childAdditionalDetail);
    }

    /**
     * Returns the ChildAdditionalDetail object that has not been archived yet for the given child id.
     */
    public ChildAdditionalDetail fetchChildAdditionalDetailById(long childId) {
        return childAdditionalDetailRepository.fetchChildAdditionalDetailByChildId(childId).orElse(null);
    }
}
