/*
 * Copyright 2016 Pivotal, Inc.
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

package com.netflix.spinnaker.orca.clouddriver.tasks.providers.cf

import spock.lang.Specification

import static com.netflix.spinnaker.orca.test.model.ExecutionBuilder.stage

class CloudFoundryServerGroupCreatorSpec extends Specification {

  def "should get operations"() {
    given:
    def ctx = [
      application      : "abc",
      account          : "abc",
      region           : "org > space",
      deploymentDetails: [[imageId: "testImageId", zone: "north-pole-1"]],
      artifact         : [type: "artifact", reference: "some-reference"],
      startApplication : true,
    ]
    def stage = stage {
      context.putAll(ctx)
    }

    when:
    def ops = new CloudFoundryServerGroupCreator().getOperations(stage)

    then:
    ops == [
      [
        "createServerGroup": [
          application   : "abc",
          credentials   : "abc",
          manifest      : null,
          region        : "org > space",
          startApplication: true,
          artifactSource: [
            type     : "artifact",
            reference: "some-reference"
          ]
        ],
      ]
    ]

  }

}
