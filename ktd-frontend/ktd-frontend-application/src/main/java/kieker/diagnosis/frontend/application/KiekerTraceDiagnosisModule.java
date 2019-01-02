/***************************************************************************
 * Copyright 2015-2019 Kieker Project (http://kieker-monitoring.net)
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
 ***************************************************************************/

package kieker.diagnosis.frontend.application;

import com.google.inject.AbstractModule;

import kieker.diagnosis.backend.base.ServiceBaseModule;
import kieker.diagnosis.backend.cache.CacheModule;
import kieker.diagnosis.backend.monitoring.MonitoringModule;
import kieker.diagnosis.frontend.base.FrontendBaseModule;

/**
 * This is the Guice module for the application.
 *
 * @author Nils Christian Ehmke
 */
final class KiekerTraceDiagnosisModule extends AbstractModule {

	@Override
	protected void configure( ) {
		install( new MonitoringModule( ) );
		install( new CacheModule( ) );
		install( new ServiceBaseModule( ) );
		install( new FrontendBaseModule( ) );
	}

}
