/***************************************************************************
 * Copyright 2015 Kieker Project (http://kieker-monitoring.net)
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

package kieker.diagnosis;

import kieker.diagnosis.mainview.Controller;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Contains the main method of this application. Do not move this class without changing the scanned package name for the Spring context.
 * 
 * @author Nils Christian Ehmke
 */
public final class Main {

	private Main() {}

	/**
	 * The main method of this application. It initializes the Spring context and uses the main controller to start everything.
	 * 
	 * @param args
	 *            The command line arguments. They have currently no effect.
	 */
	public static void main(final String[] args) {
		try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext()) {
			final String applicationPackageName = Main.class.getPackage().getName();
			context.scan(applicationPackageName);
			context.refresh();

			final Controller controller = context.getBean(Controller.class);
			controller.showView();
		}
	}
}
