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

import hudson.Plugin;

/**
 * Initializes the .NET Jenkins plugin.
 *
 * @author Shawn Black (shawn@castleblack.us)
 * @since 0.0.0
 */
public class DotNetPlugin extends Plugin {

    static final String PLUGIN_NAME = ".NET";
    static final String PLUGIN_ID = "dotnet-jenkins-plugin";

}