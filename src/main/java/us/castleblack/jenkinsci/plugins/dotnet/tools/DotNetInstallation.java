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
package us.castleblack.jenkinsci.plugins.dotnet.tools;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import hudson.EnvVars;
import hudson.Extension;
import hudson.Functions;
import hudson.Launcher;
import hudson.model.EnvironmentSpecific;
import hudson.model.Node;
import hudson.model.TaskListener;
import hudson.slaves.NodeSpecific;
import hudson.tools.ToolDescriptor;
import hudson.tools.ToolInstallation;
import hudson.tools.ToolInstaller;
import hudson.tools.ToolProperty;
import jenkins.model.Jenkins;
import jenkins.security.MasterToSlaveCallable;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * Defines a .NET tool installation.
 *
 * @author Shawn Black (shawn@castleblack.us)
 * @since 0.0.0
 */
public class DotNetInstallation extends ToolInstallation
        implements EnvironmentSpecific<DotNetInstallation>, NodeSpecific<DotNetInstallation>, Serializable {

    private static final long serialVersionUID = 1L;

    @DataBoundConstructor
    public DotNetInstallation(@Nonnull String name, @Nonnull String home, List<? extends ToolProperty<?>> properties) {
        super(name, home, properties);
    }

    @Override
    public DotNetInstallation forEnvironment(EnvVars environment) {
        return new DotNetInstallation(getName(), environment.expand(getHome()), getProperties().toList());
    }

    @Override
    public void buildEnvVars(EnvVars env) {
        if("".equals(env.get("PATH+DotNet", "")))
            env.put("PATH+DotNet", getHome());
    }

    @Override
    public DotNetInstallation forNode(@Nonnull Node node, TaskListener log) throws IOException, InterruptedException {
        return new DotNetInstallation(getName(), translateFor(node, log), getProperties().toList());
    }

    @SuppressFBWarnings("NP_NULL_ON_SOME_PATH_FROM_RETURN_VALUE")
    public String getExecutable(Launcher launcher) throws IOException, InterruptedException {
        return launcher.getChannel().call(new MasterToSlaveCallable<String, IOException>() {
            public String call() throws IOException {
                File exe = getExeFile();
                if (exe.exists()) {
                    return exe.getPath();
                }
                return null;
            }
        });
    }

    private File getExeFile() {
        return new File(getHome(), "dotnet");
    }

    @Extension
    @Symbol("dotnet")
    public static class DescriptorImpl extends ToolDescriptor<DotNetInstallation> {

        @Nonnull
        @Override
        public String getDisplayName() {
            return ".NET";
        }

        @Override
        public List<? extends ToolInstaller> getDefaultInstallers() {
            return Collections.singletonList(new DotNetInstaller(null));
        }
    }
}