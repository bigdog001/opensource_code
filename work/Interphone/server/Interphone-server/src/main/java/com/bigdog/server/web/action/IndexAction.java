/*
 * Copyright 2006 The Apache Software Foundation.
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
package com.bigdog.server.web.action;

import com.bigdog.server.web.action.base.TrackerBaseAction;
import com.bigdog.server.web.cfg.Config;
import org.springframework.stereotype.Controller;
/**
 *
 */
@Controller("apiAction")
public class IndexAction extends TrackerBaseAction{

    public String execute() throws Exception {
        if (getTrackerBaseBean().getMethod() == null) {
            return Config.getAppParams().getProperty("emptyMethod");
        } else {
            return SUCCESS;
        }

    }


}
