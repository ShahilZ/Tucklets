package com.tucklets.app.services;

import com.tucklets.app.db.repositories.ChildRepository;
import com.tucklets.app.entities.Child;
import com.tucklets.app.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;

@Service
public class AmountService {

    public BigDecimal computeTotalDonationAmount(List<Child> children) {
        return new BigDecimal(Constants.CHILD_DONATION_AMOUNT * children.size()).setScale(2, RoundingMode.CEILING);
    }
}
