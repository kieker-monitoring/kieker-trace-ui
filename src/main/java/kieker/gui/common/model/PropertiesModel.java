/***************************************************************************
 * Copyright 2014 Kieker Project (http://kieker-monitoring.net)
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

package kieker.gui.common.model;

import java.util.Observable;

public final class PropertiesModel extends Observable {

	private boolean shortComponentNames = false;
	private boolean shortOperationNames = true;

	public boolean isShortComponentNames() {
		return this.shortComponentNames;
	}

	public void setShortComponentNames(final boolean shortComponentNames) {
		this.shortComponentNames = shortComponentNames;

		this.setChanged();
		this.notifyObservers();
	}

	public boolean isShortOperationNames() {
		return this.shortOperationNames;
	}

	public void setShortOperationNames(final boolean shortOperationNames) {
		this.shortOperationNames = shortOperationNames;

		this.setChanged();
		this.notifyObservers();
	}

}