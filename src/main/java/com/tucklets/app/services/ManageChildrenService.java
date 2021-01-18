package com.tucklets.app.services;

import com.tucklets.app.configs.AwsConfig;
import com.tucklets.app.containers.admin.ChildDetailsContainer;
import com.tucklets.app.entities.Child;
import com.tucklets.app.entities.ChildAdditionalDetail;
import com.tucklets.app.utils.CalculationUtils;
import com.tucklets.app.utils.Constants;
import com.tucklets.app.utils.S3Utils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
public class ManageChildrenService {

    @Autowired
    AwsConfig awsConfig;

    @Autowired
    ChildService childService;

    @Autowired
    ChildAdditionalDetailService childAdditionalDetailService;

    @Autowired
    SimpleS3Service simpleS3Service;

    private static final String S3_IMAGES_DIRECTORY = "images/";

    /**
     * Handles the updates to the given childDetails object.
     */
    public void updateChildAndDetails(ChildDetailsContainer childDetails) throws IOException {
        Child child = childDetails.getChild();
        // TODO: Validate image?

        Child existingChild = childService.fetchChildById(child.getChildId());
        boolean isSponsored = child.getSponsored();
        // Only update existing fields if there is an existing child.
        if (existingChild != null) {
            this.addExistingFieldsToChild(child, existingChild);
        }

        // Resetting to user provided value
        child.setSponsored(isSponsored);

        // Save updates to child.
        child = childService.addChild(child);

        // Skip if image is not updated.
        if (childDetails.getChildImageFile() != null && !childDetails.getChildImageFile().isEmpty()) {
            // Create image key.
            String imageKey = String.format(
                    "image-%d.%s",
                    child.getChildId(),
                    FilenameUtils.getExtension(childDetails.getChildImageFile().getOriginalFilename()));
            // Save image location.
            childAdditionalDetailService.addImageForChild(imageKey, child.getChildId());
            simpleS3Service.uploadFile(
                    S3_IMAGES_DIRECTORY + imageKey,
                    S3Utils.convertMultiPartToFile(childDetails.getChildImageFile()),
                    awsConfig.getS3BucketName());
        }
    }


    /**
     * Fetches all available children that needs sponsorship and calculates the age.
     * Returns as a List<ChildDetailsContainer>().
     */
    public List<ChildDetailsContainer> fetchChildrenWithDetails() {
        var children = childService.fetchAvailableChildren();
        List<ChildDetailsContainer> childContainerList = new ArrayList<>();

        if (Objects.nonNull(children)) {
            for (Child child : children) {
                ChildDetailsContainer childDetailsContainer = new ChildDetailsContainer();
                var age = CalculationUtils.calculateAge(child.getBirthYear());
                childDetailsContainer.setAge(age);
                childDetailsContainer.setChild(child);
                // Fetch image location
                ChildAdditionalDetail additionalDetails =
                        childAdditionalDetailService.fetchChildAdditionalDetailById(child.getChildId());
                String imageLocation = additionalDetails != null
                        ? additionalDetails.getImageLocation()
                        : S3Utils.computeS3Key(Constants.MISSING_PHOTO_URL, awsConfig.getS3ImagesBucketUrl());
                childDetailsContainer.setChildImageLocation(imageLocation);
                childContainerList.add(childDetailsContainer);
            }
        }
        return childContainerList;
    }

    /**
     * Creates a list of ChildDetailsContainer for every child passed into this method.
     */
    public List<ChildDetailsContainer> createChildDetailsContainers(List<Child> children) {
        return children
                .stream()
                .map(this::createChildDetails)
                .collect(Collectors.toList());
    }

    /**
     * Sets the extra details of a child and returns the newly created ChildDetailsContainer.
     */
    public ChildDetailsContainer createChildDetails(Child child) {
        ChildDetailsContainer childDetailsContainer = new ChildDetailsContainer();
        childDetailsContainer.setChild(child);
        ChildAdditionalDetail childAdditionalDetail =
                childAdditionalDetailService.fetchChildAdditionalDetailById(child.getChildId());
        String imageLocation = childAdditionalDetail == null
                ? S3Utils.computeS3Key(Constants.MISSING_PHOTO_URL, awsConfig.getS3ImagesBucketUrl())
                : childAdditionalDetail.getImageLocation();
        childDetailsContainer.setChildImageLocation(imageLocation);
        return childDetailsContainer;
    }

    /**
     * Sets any additional fields for the imported Child object that is taken from his or her existing counterpart.
     * This method will port over items like childId and creationDate.
     */
    public void addExistingFieldsToChild(Child child, Child existingChild) {
        child.setChildId(existingChild.getChildId());
        child.setCreationDate(existingChild.getCreationDate());
        child.setSponsored(existingChild.getSponsored());
    }
}
