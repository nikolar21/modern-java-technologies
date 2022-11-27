package com.project.mjt.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MjtProperties {

    @Value("${cars.standard.one.beginDate}")
    private Integer euroOneBeginDate;
    @Value("${cars.standard.one.endDate}")
    private Integer euroOneEndDate;
    @Value("${cars.standard.two.beginDate}")
    private Integer euroTwoBeginDate;
    @Value("${cars.standard.two.endDate}")
    private Integer euroTwoEndDate;
    @Value("${cars.standard.three.beginDate}")
    private Integer euroThreeBeginDate;
    @Value("${cars.standard.three.endDate}")
    private Integer euroThreeEndDate;
    @Value("${cars.standard.four.beginDate}")
    private Integer euroFourBeginDate;
    @Value("${cars.standard.four.endDate}")
    private Integer euroFourEndDate;
    @Value("${cars.standard.five.beginDate}")
    private Integer euroFiveBeginDate;
    @Value("${cars.standard.five.endDate}")
    private Integer euroFiveEndDate;
    @Value("${cars.standard.six.beginDate}")
    private Integer euroSixBeginDate;
    @Value("${cars.standard.six.endDate}")
    private Integer euroSixEndDate;

    public Integer getEuroOneBeginDate() {
        return euroOneBeginDate;
    }

    public Integer getEuroOneEndDate() {
        return euroOneEndDate;
    }

    public Integer getEuroTwoBeginDate() {
        return euroTwoBeginDate;
    }

    public Integer getEuroTwoEndDate() {
        return euroTwoEndDate;
    }

    public Integer getEuroThreeBeginDate() {
        return euroThreeBeginDate;
    }

    public Integer getEuroThreeEndDate() {
        return euroThreeEndDate;
    }

    public Integer getEuroFourBeginDate() {
        return euroFourBeginDate;
    }

    public Integer getEuroFourEndDate() {
        return euroFourEndDate;
    }

    public Integer getEuroFiveBeginDate() {
        return euroFiveBeginDate;
    }

    public Integer getEuroFiveEndDate() {
        return euroFiveEndDate;
    }

    public Integer getEuroSixBeginDate() {
        return euroSixBeginDate;
    }

    public Integer getEuroSixEndDate() {
        return euroSixEndDate;
    }
}
