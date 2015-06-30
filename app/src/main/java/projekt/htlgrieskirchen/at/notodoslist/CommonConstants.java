/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package projekt.htlgrieskirchen.at.notodoslist;

/**
 *
 * A set of constants used by all of the components in this application. To use these constants
 * the components implement the interface.
 */

public final class CommonConstants {

    public CommonConstants() {

        // don't allow the class to be instantiated
    }

    // Milliseconds in the snooze duration, which translates
    // to 20 seconds.
    public static final int SNOOZE_DURATION = 20000;
    public static final int DEFAULT_TIMER_DURATION = 10000;
    public static final String ACTION_SNOOZE = "projekt.htlgrieskirchen.at.notodoslist.ACTION_SNOOZE";
    public static final String ACTION_DISMISS = "projekt.htlgrieskirchen.at.notodoslist.ACTION_DISMISS";
    public static final String ACTION_PING = "projekt.htlgrieskirchen.at.nododoslist.ACTION_PING";
    public static final String EXTRA_MESSAGE= "projekt.htlgrieskirchen.at.nododoslist.EXTRA_MESSAGE";
    public static final String EXTRA_TIMER = "projekt.htlgrieskirchen.at.notodoslist.EXTRA_TIMER";
    public static final int NOTIFICATION_ID = 001;
    public static final String DEBUG_TAG = "notodoslist";
}
