package accpt;

import com.mtp.pounder.PounderModel;
import com.mtp.pounder.PounderFrame;
import com.mtp.pounder.ComponentConduit;

import java.awt.Component;

/**

Instance of Pounder that will not interfere with system preferences.

@author Matthew Pekar

**/
public class PounderInstance implements ComponentConduit {

	public PounderModel model;
	public PounderFrame frame;

	public PounderInstance() {
		this.model = new PounderModel(false);
		this.model.getPreferences().setSavePrefsOnExit(false);
		this.frame = new PounderFrame(model);
	}

	public Component getComponent() {
		return frame;
	}

}
