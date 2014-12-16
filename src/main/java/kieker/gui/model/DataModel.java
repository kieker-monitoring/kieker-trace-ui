package kieker.gui.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.concurrent.TimeUnit;

import kieker.common.record.misc.KiekerMetadataRecord;
import kieker.gui.model.domain.AggregatedExecutionEntry;
import kieker.gui.model.domain.ExecutionEntry;
import kieker.gui.model.domain.RecordEntry;
import kieker.gui.model.importer.ImportAnalysisConfiguration;
import teetime.framework.Analysis;

/**
 * A container for data used within this application.
 *
 * @author Nils Christian Ehmke
 */
public final class DataModel extends Observable {

	private List<RecordEntry> records = Collections.emptyList();
	private List<ExecutionEntry> traces = Collections.emptyList();
	private List<ExecutionEntry> failureContainingTraces = Collections.emptyList();
	private List<ExecutionEntry> failedTraces = Collections.emptyList();
	private List<AggregatedExecutionEntry> aggregatedTraces = Collections.emptyList();
	private String shortTimeUnit = "";

	public DataModel() {}

	public void loadMonitoringLogFromFS(final String directory) {
		// Load and analyze the monitoring logs from the given directory
		final File importDirectory = new File(directory);
		final ImportAnalysisConfiguration analysisConfiguration = new ImportAnalysisConfiguration(importDirectory);
		final Analysis analysis = new Analysis(analysisConfiguration);
		analysis.init();
		analysis.start();

		// Store the results from the analysis
		this.records = analysisConfiguration.getRecordsList();
		this.traces = analysisConfiguration.getTracesList();
		this.failedTraces = analysisConfiguration.getFailedTracesList();
		this.failureContainingTraces = analysisConfiguration.getFailureContainingTracesList();
		this.aggregatedTraces = analysisConfiguration.getAggregatedTraces();

		final List<KiekerMetadataRecord> metadataRecords = analysisConfiguration.getMetadataRecords();
		if (metadataRecords.size() == 1) {
			final KiekerMetadataRecord metadataRecord = metadataRecords.get(0);
			this.shortTimeUnit = this.convertToShortTimeUnit(TimeUnit.valueOf(metadataRecord.getTimeUnit()));
		} else {
			this.shortTimeUnit = this.convertToShortTimeUnit(null);
		}

		this.setChanged();
		this.notifyObservers();
	}

	private String convertToShortTimeUnit(final TimeUnit timeUnit) {
		final String result;

		switch (timeUnit) {
		case DAYS:
			result = "d";
			break;
		case HOURS:
			result = "h";
			break;
		case MICROSECONDS:
			result = "us";
			break;
		case MILLISECONDS:
			result = "ms";
			break;
		case MINUTES:
			result = "m";
			break;
		case NANOSECONDS:
			result = "ns";
			break;
		case SECONDS:
			result = "s";
			break;
		default:
			result = "";
			break;
		}

		return result;
	}

	public List<RecordEntry> getRecordsCopy() {
		return new ArrayList<>(this.records);
	}

	public List<ExecutionEntry> getTracesCopy() {
		return new ArrayList<>(this.traces);
	}

	public List<ExecutionEntry> getFailedTracesCopy() {
		return new ArrayList<>(this.failedTraces);
	}

	public List<ExecutionEntry> getFailureContainingTracesCopy() {
		return new ArrayList<>(this.failureContainingTraces);
	}

	public List<AggregatedExecutionEntry> getAggregatedTracesCopy() {
		return new ArrayList<>(this.aggregatedTraces);
	}

	public String getShortTimeUnit() {
		return this.shortTimeUnit;
	}

}