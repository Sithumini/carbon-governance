/*
*​Copyright (c) 2015, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.​
*
*WSO2 Inc. licenses this file to you under the Apache License,
*Version 2.0 (the "License"); you may not use this file except
*in compliance with the License.
*You may obtain a copy of the License at
*
*http://www.apache.org/licenses/LICENSE-2.0
*
*Unless required by applicable law or agreed to in writing,
*software distributed under the License is distributed on an
*"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
*KIND, either express or implied.  See the License for the
*specific language governing permissions and limitations
*under the License.
*/

package org.wso2.carbon.governance.resourcecompare.ui;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.governance.resourcecompare.ui.utils.PropertyReader;
import org.wso2.carbon.governance.resourcecompare.ui.utils.ResourceExtensionValidator;
import org.wso2.carbon.registry.common.services.RegistryAbstractAdmin;
import org.wso2.carbon.utils.xml.StringUtils;

/*
*
* Class for compare wsdl documents
*
*/
public class ResourceComparator extends RegistryAbstractAdmin {

    private static final Log LOG = LogFactory.getLog(ResourceComparator.class);
    private static final String PROP_FILE_PATH = "org/wso2/carbon/governance/wsdltool/ui/i18n/ExtensionDiffViewMapping.properties";
    private static final String MESSAGE_INVALID_EXTENSION = "resource.compare.invalid.ext";
    private static final String MESSAGE_INCOMPATIBLE_EXTENSION = "resource.compare.incompatible.ext";
    private static final String MESSAGE_EXTENSION_ERROR = "resource.compare.ext.error";
    private static final String MESSAGE_DIFF_VIEW_ERROR = "resource.compare.diffview.error";

    // No-args constructor
    public ResourceComparator() {
    }

    /**
     * Method for get the type of the difference viewer
     * @param resource1Path resource 1 path
     * @param resource2Path resource 2 path
     * @return result msg
     */
    public String getDiffViewType(String resource1Path, String resource2Path) {
        String diffViewType;
        String result = null;
        String extension;
        ResourceExtensionValidator fev = new ResourceExtensionValidator();
        try {
            if ((!StringUtils.isEmpty(resource1Path) && !StringUtils.isEmpty(resource2Path)) ||
                    (!"".equals(resource1Path) && !"".equals(resource2Path))) {

                // Get the extensions of the resources
                String resource1Extension = fev.getFileExtension(resource1Path);
                String resource2Extension = fev.getFileExtension(resource2Path);

                if (!StringUtils.isEmpty(resource1Extension) && !StringUtils.isEmpty(resource2Extension)) {
                    // Check the compatibility of the extensions
                    if (resource1Extension.equalsIgnoreCase(resource2Extension)) {
                        // Check for resource extension validity (support by the feature)
                        if (fev.isValidExtension(resource1Path) && fev.isValidExtension(resource2Path)) {
                            // Get the diffView type
                            extension = resource1Extension;
                            PropertyReader propertyReader = new PropertyReader();
                            String propKey = extension;
                            diffViewType = propertyReader.readPropValues(PROP_FILE_PATH, propKey);
                            result = diffViewType;
                        } else {
                            result = MESSAGE_INVALID_EXTENSION;
                        }
                    } else {
                        result = MESSAGE_INCOMPATIBLE_EXTENSION;
                    }

                } else {
                    result = MESSAGE_EXTENSION_ERROR;
                }
            } else {
                result = MESSAGE_DIFF_VIEW_ERROR;
            }
        } catch (NullPointerException e) {
            String msg = "Error in retrieving file extension.";
            LOG.error(msg, e);
        }
        return result;
    }

}
