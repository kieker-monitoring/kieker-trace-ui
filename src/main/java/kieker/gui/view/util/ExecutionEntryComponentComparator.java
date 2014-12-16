package kieker.gui.view.util;

import kieker.gui.model.domain.ExecutionEntry;

import org.eclipse.swt.SWT;

public class ExecutionEntryComponentComparator extends AbstractDirectedComparator<ExecutionEntry> {

	@Override
	public int compare(final ExecutionEntry arg0, final ExecutionEntry arg1) {
		int result = arg0.getComponent().compareTo(arg1.getOperation());
		if (this.getDirection() == SWT.UP) {
			result = -result;
		}
		return result;

	}

}