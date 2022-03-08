/*
 * Copyright 2014-2019 Groupon, Inc
 * Copyright 2014-2019 The Billing Project, LLC
 *
 * The Billing Project licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package org.killbill.billing.payment.core.janitor;

import org.joda.time.DateTime;
import org.killbill.billing.payment.PaymentTestSuiteNoDB;
import org.killbill.billing.payment.api.TransactionStatus;
import org.testng.annotations.Test;

import com.google.inject.Inject;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

public class TestIncompletePaymentAttemptTask extends PaymentTestSuiteNoDB {

    @Inject
    protected IncompletePaymentAttemptTask incompletePaymentAttemptTask;

    @Test(groups = "fast")
    public void testGetNextNotificationTime() {
        final DateTime initTime = clock.getUTCNow();

        // Based on config "5m,1h,1d,1d,1d,1d,1d"
        for (int i = 1; i < 8; i++) {
            final DateTime nextTime = incompletePaymentAttemptTask.getNextNotificationTime(TransactionStatus.UNKNOWN, i, internalCallContext);
            assertNotNull(nextTime);
            assertTrue(nextTime.compareTo(initTime) > 0);
            if (i == 1) {
                assertTrue(nextTime.compareTo(initTime.plusHours(1).plusSeconds(1)) < 0);
            } else if (i == 2) {
                assertTrue(nextTime.compareTo(initTime.plusDays(1).plusSeconds(1)) < 0);
            } else if (i == 3) {
                assertTrue(nextTime.compareTo(initTime.plusDays(1).plusSeconds(1)) < 0);
            } else if (i == 4) {
                assertTrue(nextTime.compareTo(initTime.plusDays(1).plusSeconds(1)) < 0);
            } else if (i == 5) {
                assertTrue(nextTime.compareTo(initTime.plusDays(1).plusSeconds(1)) < 0);
            } else if (i == 6) {
                assertTrue(nextTime.compareTo(initTime.plusDays(1).plusSeconds(1)) < 0);
            }
        }
        assertNull(incompletePaymentAttemptTask.getNextNotificationTime(TransactionStatus.UNKNOWN, 8, internalCallContext));
    }
}
