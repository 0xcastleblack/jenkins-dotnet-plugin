/*
 * This file is part of the .NET Jenkins plugin.
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
package us.castleblack.jenkinsci.plugins.dotnet;

import hudson.CopyOnWrite;
import hudson.Extension;
import hudson.model.AbstractProject;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.Builder;
import hudson.tools.ToolInstallation;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Map;

/**
 * Defines a .NET builder.
 *
 * @author Shawn Black (shawn@castleblack.us)
 * @since 0.0.0
 */
public class DotNetBuilder extends Builder {

    @DataBoundConstructor
    public DotNetBuilder() {
    }

    @Override
    public DescriptorImpl getDescriptor() {
        return (DescriptorImpl) super.getDescriptor();
    }

    @Extension
    @Symbol("dotnet")
    public static final class DescriptorImpl extends BuildStepDescriptor<Builder> {

        @CopyOnWrite
        private volatile DotNetInstallation[] installations = new DotNetInstallation[0];

        public DescriptorImpl() {
            load();
        }

        protected DescriptorImpl(Class<? extends DotNetBuilder> clazz) {
            super(clazz);
        }

        public DotNetInstallation.DescriptorImpl getToolDescriptor() {
            return ToolInstallation.all().get(DotNetInstallation.DescriptorImpl.class);
        }

        @Override
        public boolean isApplicable(Class<? extends AbstractProject> jobType) {
            return true;
        }

        protected void convert(Map<String, Object> oldPropertyBag) {
            if (oldPropertyBag.containsKey("installations")) {
                installations = (DotNetInstallation[]) oldPropertyBag.get("installations");
            }
        }

        @Nonnull
        @Override
        public String getDisplayName() {
            return Messages.Builder_DisplayName();
        }

        public DotNetInstallation[] getInstallations() {
            return Arrays.copyOf(installations, installations.length);
        }

        public void setInstallations(DotNetInstallation... installations) {
            this.installations = installations;
            save();
        }
    }
}