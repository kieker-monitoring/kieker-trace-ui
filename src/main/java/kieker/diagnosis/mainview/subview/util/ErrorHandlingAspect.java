package kieker.diagnosis.mainview.subview.util;

import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

@Aspect
public class ErrorHandlingAspect {

	private final ResourceBundle resourceBundle = ResourceBundle.getBundle("locale.kieker.diagnosis.mainview.subview.util.errorhandling", Locale.getDefault());

	@Pointcut("execution(@kieker.diagnosis.mainview.subview.util.ErrorHandling * *(..))")
	public void errorHandlingRequested() {
		// Aspect Declaration (MUST be empty)
	}

	@Around("errorHandlingRequested() && this(thisObject)")
	public Object methodHandling(final Object thisObject, final ProceedingJoinPoint thisJoinPoint) throws Throwable {
		try {
			return thisJoinPoint.proceed();
		} catch (final Exception ex) {
			final Logger logger = LogManager.getLogger(thisObject.getClass());
			logger.error(ex.getMessage(), ex);
			
			final Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText(ex.getLocalizedMessage());
			alert.setTitle(resourceBundle.getString("error"));
			alert.setHeaderText(resourceBundle.getString("errorHeader"));
			final Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
			stage.getIcons().add(new Image("kieker-logo.png"));
			alert.showAndWait();

			return null;
		}
	}

}
