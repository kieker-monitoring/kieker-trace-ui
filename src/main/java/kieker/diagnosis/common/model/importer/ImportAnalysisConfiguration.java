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

package kieker.diagnosis.common.model.importer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import kieker.common.record.IMonitoringRecord;
import kieker.common.record.flow.IFlowRecord;
import kieker.common.record.misc.KiekerMetadataRecord;
import kieker.diagnosis.common.domain.AggregatedTrace;
import kieker.diagnosis.common.domain.Trace;
import kieker.diagnosis.common.model.importer.stages.ReadingComposite;
import kieker.diagnosis.common.model.importer.stages.TraceAggregationComposite;
import kieker.diagnosis.common.model.importer.stages.TraceReconstructionComposite;
import teetime.framework.AnalysisConfiguration;
import teetime.framework.pipe.IPipeFactory;
import teetime.framework.pipe.PipeFactoryRegistry.PipeOrdering;
import teetime.framework.pipe.PipeFactoryRegistry.ThreadCommunication;
import teetime.stage.CollectorSink;
import teetime.stage.MultipleInstanceOfFilter;

/**
 * A configuration for the import and analysis of monitoring logs.
 * 
 * @author Nils Christian Ehmke
 */
public final class ImportAnalysisConfiguration extends AnalysisConfiguration {

	private final List<Trace> traces = new ArrayList<>(1000);
	private final List<Trace> failedTraces = new ArrayList<>(1000);
	private final List<Trace> failureContainingTraces = new ArrayList<>(1000);

	private final List<AggregatedTrace> aggregatedTraces = new ArrayList<>(1000);
	private final List<AggregatedTrace> failedAggregatedTraces = new ArrayList<>(1000);
	private final List<AggregatedTrace> failureContainingAggregatedTraces = new ArrayList<>(1000);

	private final List<KiekerMetadataRecord> metadataRecords = new ArrayList<>(1000);

	public ImportAnalysisConfiguration(final File importDirectory) {
		// Create the stages
		final ReadingComposite reader = new ReadingComposite(importDirectory);
		final MultipleInstanceOfFilter<IMonitoringRecord> typeFilter = new MultipleInstanceOfFilter<>();
		final TraceReconstructionComposite reconstruction = new TraceReconstructionComposite(this.traces, this.failedTraces, this.failureContainingTraces);
		final TraceAggregationComposite aggregation = new TraceAggregationComposite(this.aggregatedTraces, this.failedAggregatedTraces, this.failureContainingAggregatedTraces);

		final CollectorSink<KiekerMetadataRecord> metadataCollector = new CollectorSink<>(this.metadataRecords);

		// Connect the stages
		final IPipeFactory pipeFactory = AnalysisConfiguration.PIPE_FACTORY_REGISTRY.getPipeFactory(ThreadCommunication.INTRA, PipeOrdering.ARBITRARY, false);
		pipeFactory.create(reader.getOutputPort(), typeFilter.getInputPort());
		pipeFactory.create(typeFilter.getOutputPortForType(IFlowRecord.class), reconstruction.getInputPort());
		pipeFactory.create(reconstruction.getOutputPort(), aggregation.getInputPort());
		pipeFactory.create(typeFilter.getOutputPortForType(KiekerMetadataRecord.class), metadataCollector.getInputPort());

		// Make sure that the producer is executed by the analysis
		super.addThreadableStage(reader);
	}

	public List<Trace> getTracesList() {
		return this.traces;
	}

	public List<Trace> getFailedTracesList() {
		return this.failedTraces;
	}

	public List<Trace> getFailureContainingTracesList() {
		return this.failureContainingTraces;
	}

	public List<AggregatedTrace> getFailedAggregatedTracesList() {
		return this.failedAggregatedTraces;
	}

	public List<AggregatedTrace> getFailureContainingAggregatedTracesList() {
		return this.failureContainingAggregatedTraces;
	}

	public List<AggregatedTrace> getAggregatedTraces() {
		return this.aggregatedTraces;
	}

	public List<KiekerMetadataRecord> getMetadataRecords() {
		return this.metadataRecords;
	}

}