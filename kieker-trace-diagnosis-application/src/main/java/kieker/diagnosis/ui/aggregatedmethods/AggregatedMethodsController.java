package kieker.diagnosis.ui.aggregatedmethods;

import java.util.List;

import com.google.inject.Singleton;

import kieker.diagnosis.architecture.exception.BusinessException;
import kieker.diagnosis.architecture.exception.BusinessRuntimeException;
import kieker.diagnosis.architecture.ui.ControllerBase;
import kieker.diagnosis.service.aggregatedmethods.AggregatedMethodsFilter;
import kieker.diagnosis.service.aggregatedmethods.AggregatedMethodsService;
import kieker.diagnosis.service.data.AggregatedMethodCall;
import kieker.diagnosis.service.export.CSVData;
import kieker.diagnosis.service.settings.SettingsService;
import kieker.diagnosis.ui.main.MainController;

@Singleton
class AggregatedMethodsController extends ControllerBase<AggregatedMethodsViewModel> {

	private List<AggregatedMethodCall> ivMethods;
	private String ivDurationSuffix;
	private int ivTotalMethods;

	/**
	 * This action is performed once during the application's start.
	 */
	public void performInitialize( ) {
		getViewModel( ).updatePresentationStatus( 0, 0 );
		getViewModel( ).updatePresentationFilter( new AggregatedMethodsFilter( ) );
		getViewModel( ).updatePresentationDetails( null );
	}

	public void performSearch( ) {
		try {
			// Get the filter input from the user
			final AggregatedMethodsFilter filter = getViewModel( ).savePresentationFilter( );

			// Find the methods to display
			final AggregatedMethodsService methodsService = getService( AggregatedMethodsService.class );
			final List<AggregatedMethodCall> methods = methodsService.searchMethods( filter );
			final int totalMethods = methodsService.countMethods( );

			// Update the view
			getViewModel( ).updatePresentationMethods( methods );
			getViewModel( ).updatePresentationStatus( methods.size( ), totalMethods );
		} catch ( final BusinessException ex ) {
			throw new BusinessRuntimeException( ex );
		}

	}

	public void performPrepareRefresh( ) {
		// Get the data
		final AggregatedMethodsService methodsService = getService( AggregatedMethodsService.class );
		ivMethods = methodsService.searchMethods( new AggregatedMethodsFilter( ) );
		ivTotalMethods = methodsService.countMethods( );

		final SettingsService settingsService = getService( SettingsService.class );
		ivDurationSuffix = settingsService.getCurrentDurationSuffix( );
	}

	/**
	 * This action is performed, when a refresh of the view is required
	 */
	public void performRefresh( ) {
		// Reset the filter
		final AggregatedMethodsFilter filter = new AggregatedMethodsFilter( );
		getViewModel( ).updatePresentationFilter( filter );

		// Update the table
		getViewModel( ).updatePresentationMethods( ivMethods );
		getViewModel( ).updatePresentationStatus( ivMethods.size( ), ivTotalMethods );

		// Update the column header of the table
		getViewModel( ).updatePresentationDurationColumnHeader( ivDurationSuffix );
	}

	public void performSetParameter( final Object aParameter ) {
		if ( aParameter instanceof AggregatedMethodsFilter ) {
			final AggregatedMethodsFilter filter = (AggregatedMethodsFilter) aParameter;
			getViewModel( ).updatePresentationFilter( filter );

			performSearch( );
		}
	}

	public void performSaveAsFavorite( ) {
		try {
			final AggregatedMethodsFilter filter = getViewModel( ).savePresentationFilter( );
			getController( MainController.class ).performSaveAsFavorite( AggregatedMethodsView.class, filter );
		} catch ( final BusinessException ex ) {
			throw new BusinessRuntimeException( ex );
		}
	}

	public void performSelectionChange( ) {
		final AggregatedMethodCall methodCall = getViewModel( ).getSelected( );
		getViewModel( ).updatePresentationDetails( methodCall );
	}

	public void performJumpToMethods( ) {
		final AggregatedMethodCall methodCall = getViewModel( ).getSelected( );

		if ( methodCall != null ) {
			getController( MainController.class ).performJumpToMethods( methodCall );
		}
	}

	public void performExportToCSV( ) {
		final CSVData csvData = getViewModel( ).savePresentationAsCSV( );
		getController( MainController.class ).performExportToCSV( csvData );
	}

}