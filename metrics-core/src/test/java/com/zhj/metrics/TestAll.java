package com.zhj.metrics;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ MonitorConfigurationTestCase.class, DefaultMetricsSystemTestCase.class })
public class TestAll {

}
