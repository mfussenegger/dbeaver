/*
 * DBeaver - Universal Database Manager
 * Copyright (C) 2010-2023 DBeaver Corp and others
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jkiss.dbeaver.ui.editors.sql.ai.internal;

import org.eclipse.osgi.util.NLS;

public class AIUIMessages extends NLS {
    static final String BUNDLE_NAME = "org.jkiss.dbeaver.ui.editors.sql.ai.internal.AIUIMessages"; //$NON-NLS-1$


    public static String gpt_preference_page_checkbox_enable_ai_label;
    public static String gpt_preference_page_checkbox_enable_ai_tip;

    public static String gpt_preference_page_group_authorization;
    public static String gpt_preference_page_selector_token;

    public static String gpt_preference_page_token_info;

    public static String gpt_preference_page_completion_group;
    public static String gpt_preference_page_completion_include_source_label;
    public static String gpt_preference_page_completion_include_source_tip;
    public static String gpt_preference_page_completion_execute_immediately_label;
    public static String gpt_preference_page_completion_execute_immediately_tip;

    public static String gpt_preference_page_group_model;



    public static String gpt_preference_page_combo_engine;


    public static String gpt_preference_page_info_model;

    public static String gpt_preference_page_group_model_advanced;
    public static String gpt_preference_page_text_temperature;

    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, AIUIMessages.class);
    }

    private AIUIMessages() {
    }
}
