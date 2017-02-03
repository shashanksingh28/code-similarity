// This example is from _Java Examples in a Nutshell_. (http://www.oreilly.com)
// Copyright (c) 1997 by David Flanagan
// This example is provided WITHOUT ANY WARRANTY either expressed or implied.
// You may study, use, modify, and distribute it for non-commercial purposes.
// For any commercial use, see http://www.davidflanagan.com/javaexamples

package oreilly.beans.yesno;
import java.beans.*;
import java.lang.reflect.*;
import java.awt.*;
/** The BeanInfo class for the YesNoDialog bean */
public class YesNoDialogBeanInfo extends SimpleBeanInfo {

  /** Return an icon for the bean.  We should really check the kind argument
   *  to see what size icon the beanbox wants, but since we only have one
   *  icon to offer, we just return it and let the beanbox deal with it */
  public Image getIcon(int kind) {
    return loadImage("YesNoDialogIcon.gif");
  }

  /** Return a descriptor for the bean itself.  It specifies a customizer
   *  for the bean class.  We could also add a description string here */
  public BeanDescriptor getBeanDescriptor() {
    return new BeanDescriptor(YesNoDialog.class, YesNoDialogCustomizer.class);
  }

  /** This is a convenience routine for creating PropertyDescriptor objects */
  public static PropertyDescriptor property(String name, String description)
       throws IntrospectionException
  {
    PropertyDescriptor p = new PropertyDescriptor(name, YesNoDialog.class);
    p.setShortDescription(description);
    return p;
  }

  /** This method returns an array of PropertyDescriptor objects that specify
   *  additional information about the properties supported by the bean.
   *  By explicitly specifying property descriptors, we are able to provide
   *  simple help strings for each property; these would not be available to
   *  the beanbox through simple introspection.  We are also able to register
   *  special property editors for two of the properties
   */
  public PropertyDescriptor[] getPropertyDescriptors() {
    try {
      PropertyDescriptor[] props = {
        property("title", "The string that appears in the dialog title bar"),
        property("message", "The string that appears in the dialog body"),
        property("yesLabel", "The label for the 'Yes' button, if any"),
        property("noLabel", "The label for the 'No' button, if any"),
        property("cancelLabel", "The label for the 'Cancel' button, if any"),
        property("alignment", "The alignment of the message text"),
        property("font", "The font to use for message and buttons"),
        property("background", "The background color for the dialog"),
        property("foreground", "The text color for message and buttons")
      };

      props[1].setPropertyEditorClass(YesNoDialogMessageEditor.class);
      props[5].setPropertyEditorClass(YesNoDialogAlignmentEditor.class);

      return props;
    }
    catch (IntrospectionException e) {return super.getPropertyDescriptors(); }
  }

  /** The message property is most often customized; make it the default */
  public int getDefaultPropertyIndex() { return 1; }
  /** This is a convenience method for creating MethodDescriptors.  Note that
   *  it assumes we are talking about methods with no arguments */
  public static MethodDescriptor method(String name, String description)
       throws NoSuchMethodException, SecurityException {
    Method m = YesNoDialog.class.getMethod(name, new Class[] {});
    MethodDescriptor md = new MethodDescriptor(m);
    md.setShortDescription(description);
    return md;
  }

  /** This method returns an array of method descriptors for the supported
   *  methods of a bean. This allows us to provide useful description strings,
   *  but it also allows us to filter out non-useful methods like wait()
   *  and notify() that the bean inherits and which might otherwise be
   *  displayed by the beanbox.
   */
  public MethodDescriptor[] getMethodDescriptors() {
    try {
      MethodDescriptor[] methods = {
        method("display", "Pop up the dialog; make it visible")
      };
      return methods;
    }
    catch (Exception e) {
      return super.getMethodDescriptors();
    }
  }
}
