/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.background.sync;


import android.content.Context;
import android.support.annotation.NonNull;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

import java.util.concurrent.TimeUnit;

public class ReminderUtilities {
     static final int REMINDER_INTERVAL_MINUTES = 15;
     static final int REMINDER_INTERVAL_SECONDS = (int) (TimeUnit.MINUTES.toSeconds(REMINDER_INTERVAL_MINUTES));
     static final int SYNC_FLEXTIME_SECONDS = REMINDER_INTERVAL_SECONDS;

     static final String REMINDER_JOB_TAG = "hydration_reminder_tag";

     static boolean sInitialized;
    synchronized public static void scheduleChargingReminder(@NonNull final Context context) {

        if (sInitialized) return;
        Driver driver = new GooglePlayDriver(context);
        // COMPLETED (19) Create a new FirebaseJobDispatcher with the driver
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(driver);
        Job constraintReminderJob = dispatcher.newJobBuilder().setTag(REMINDER_JOB_TAG)
                .setConstraints(Constraint.DEVICE_CHARGING).setLifetime(Lifetime.FOREVER).setRecurring(true)
                .setTrigger(Trigger.executionWindow(REMINDER_INTERVAL_SECONDS, REMINDER_INTERVAL_SECONDS + SYNC_FLEXTIME_SECONDS))
                .setReplaceCurrent(true).build();
        dispatcher.schedule(constraintReminderJob);
        sInitialized = true;
    }

}