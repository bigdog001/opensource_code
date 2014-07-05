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
package com.facemail.server.mobile.action;

import com.facemail.server.mobile.action.base.TrackerBaseAction;
import com.facemail.server.mobile.bean.base.TrackerBaseBean;
import com.facemail.server.mobile.cfg.Config;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.conversion.annotations.Conversion;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;


/**
 *
 */
@Controller("apiAction")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Conversion()
public class IndexAction extends TrackerBaseAction implements ModelDriven<TrackerBaseBean> {

    public String execute() throws Exception {
        if (getTrackerBaseBean().getMethod() == null) {
            return Config.getAppParams().getProperty("emptyMethod");
        } else {
            return SUCCESS;
        }

    }

    public TrackerBaseBean getModel() {
        return getTrackerBaseBean();
    }

}
