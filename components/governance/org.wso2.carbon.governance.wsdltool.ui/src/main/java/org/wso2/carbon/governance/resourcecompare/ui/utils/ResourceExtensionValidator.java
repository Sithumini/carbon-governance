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
package org.wso2.carbon.governance.resourcecompare.ui.utils;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Class for file extension validate related functions
 */
public class ResourceExtensionValidator {

    private static final Log LOG = LogFactory.getLog(ResourceExtensionValidator.class);

    /**
     * Method for getting file extension from given path
     * @param filePath file path
     * @return valid file extensions
     */
    public String getFileExtension(String filePath) {
        String ext = null;
        if (!StringUtils.isEmpty(filePath)) {
            // Get the extension of the resource
            ext = FilenameUtils.getExtension(filePath);
        }
        return ext;

    }

    /**
     * Method for getting valid file extension from property file
     * @return array of valid file extensions
     */
    private String[] getExtPropValues() {
        String validExtensionsArray[] = null;
        PropertyReader propertyReader = new PropertyReader();
        String propFilePath = "org/wso2/carbon/governance/wsdltool/ui/i18n/FileExtensions.properties";
        String propKey = "resource.compare.ext";

            // Get the extension property value array split up by comma
            validExtensionsArray = propertyReader.readPropValues(propFilePath, propKey).split(",");

        return validExtensionsArray;
    }

    /**
     * Method for validate file extension from given path
     * @param resourcePath file path
     * @return validity status
     */
    public boolean isValidExtension(String resourcePath) {
        boolean status = false;
        String fileExt;
        String validExtArray[];
        try {
            // Get the file extension
            fileExt = getFileExtension(resourcePath);
            // Valid extensions array
            validExtArray = getExtPropValues();
            for (int i = 0; i < validExtArray.length; i++) {
                if (fileExt.equalsIgnoreCase(validExtArray[i])) {
                    status = true;
                    break;
                }
            }
        } catch (NullPointerException e) {
            String msg = "Error in retrieving file extension";
            LOG.error(msg, e);
        }

        return status;
    }

}
