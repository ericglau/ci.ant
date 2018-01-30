/**
 * (C) Copyright IBM Corporation 2018.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.wasdev.wlp.ant.install;

import java.io.File;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import net.wasdev.wlp.ant.ProductInfoTask;

public class ProductInfoTest {

    private static final File installDir = new File("target/wlp");

    private static class MyProject extends Project {

        @Override
        public void log(Task t, String message, int level) {
            System.out.println(message);
        }
    }

    @BeforeClass
    public static void setup() {
        InstallLibertyTask install = new InstallLibertyTask();
        install.setProject(new Project());
        install.setBaseDir(installDir.getAbsolutePath());
        install.setType("webProfile7");
        install.execute();
    }

    @AfterClass
    public static void tearDown() {
        delete(installDir);
    }

    private static void delete(File f) {
        if (f.isFile()) {
            f.delete();
        } else if (f.isDirectory()) {
            File[] files = f.listFiles();
            if (files != null) {
                for (File file : files) {
                    delete(file);
                }
            }
            f.delete();
        }
    }

    @Test
    public void testBasicCompile() throws URISyntaxException {
        ProductInfoTask task = createTask();
        task.execute();

        List<ProductInfo> productInfoList = task.getProductInfoList();
        String productId = productInfoList.get(0).getProductId();
        ProductVersion version = productInfoList.get(0).getProductVersion();
        Assert.assertEquals("com.ibm.websphere.appserver", productId);
        Assert.assertTrue("productVersion should be greater than or equal to 17.0.0.4", version.compareTo(ProductVersion.parseVersion("17.0.0.4")) >= 0);
    }

    private ProductInfoTask createTask() {
        ProductInfoTask productVersion = new ProductInfoTask();
        productVersion.setProject(new MyProject());
        productVersion.setInstallDir(new File(installDir, "wlp"));
        return productVersion;
    }

}
