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

package kieker.gui.view.util;

import kieker.gui.model.domain.Execution;

import org.eclipse.swt.SWT;

public class ExecutionEntryOperationComparator extends AbstractDirectedComparator<Execution> {

	@Override
	public int compare(final Execution arg0, final Execution arg1) {
		int result = arg0.getOperation().compareTo(arg1.getOperation());
		if (this.getDirection() == SWT.UP) {
			result = -result;
		}
		return result;

	}

}
