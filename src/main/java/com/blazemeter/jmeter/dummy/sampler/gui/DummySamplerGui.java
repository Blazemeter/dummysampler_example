package com.blazemeter.jmeter.dummy.sampler.gui;

import com.blazemeter.jmeter.dummy.sampler.DummySampler;
import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import org.apache.jmeter.samplers.gui.AbstractSamplerGui;
import org.apache.jmeter.testelement.TestElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DummySamplerGui extends AbstractSamplerGui {

  private static final Logger LOG = LoggerFactory.getLogger(DummySamplerGui.class);

  private final JTextField responseTime = new JTextField();
  private final JTextField label = new JTextField();
  private final JTextField responseCode = new JTextField();
  private final JCheckBox success = new JCheckBox("Success", true);


  public DummySamplerGui() {
    setLayout(new BorderLayout());
    setBorder(makeBorder());
    add(makeTitlePanel(), BorderLayout.NORTH);
    add(createDummySamplerPanel(), BorderLayout.CENTER);
  }

  private JPanel createDummySamplerPanel(){
    JPanel dummySamplerPanel = new JPanel();
    dummySamplerPanel.setBorder(BorderFactory.createTitledBorder("Config"));
    GroupLayout layout = new GroupLayout(dummySamplerPanel);
    dummySamplerPanel.setLayout(layout);

    JLabel labelLabel = new JLabel("Label");
    JLabel responseTimeLabel = new JLabel("Response Time (ms)");
    JLabel responseCodeLabel = new JLabel("Response Code");

    layout.setHorizontalGroup(
        layout.createParallelGroup(Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(labelLabel)
                .addPreferredGap(ComponentPlacement.RELATED)
                .addComponent(label))
            .addGroup(layout.createSequentialGroup()
                .addComponent(responseTimeLabel)
                .addPreferredGap(ComponentPlacement.RELATED)
                .addComponent(responseTime))
            .addGroup(layout.createSequentialGroup()
                .addComponent(responseCodeLabel)
                .addPreferredGap(ComponentPlacement.RELATED)
                .addComponent(responseCode))
            .addGroup(layout.createSequentialGroup()
                .addComponent(success)));
    layout.setVerticalGroup(
        layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(Alignment.LEADING)
                .addComponent(labelLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE,
                    GroupLayout.PREFERRED_SIZE)
                .addComponent(label, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE,
                    GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createParallelGroup(Alignment.LEADING)
                .addComponent(responseTimeLabel, GroupLayout.PREFERRED_SIZE,
                    GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                .addComponent(responseTime, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE,
                    GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createParallelGroup(Alignment.LEADING)
                .addComponent(responseCodeLabel, GroupLayout.PREFERRED_SIZE,
                    GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                .addComponent(responseCode, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE,
                    GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createParallelGroup(Alignment.LEADING)
                .addComponent(success, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE,
                    GroupLayout.PREFERRED_SIZE)));
    return dummySamplerPanel;
  }

  @Override
  public String getLabelResource() {
    return "Dummy Sampler";
  }

  @Override
  public String getStaticLabel() {
    return getLabelResource();
  }

  @Override
  public TestElement createTestElement() {
    DummySampler dummySampler = new DummySampler();
    configureTestElement(dummySampler);
    return dummySampler;
  }

  @Override
  public void modifyTestElement(TestElement element) {
    super.configureTestElement(element);
    if (element instanceof DummySampler) {
      DummySampler dummySampler = (DummySampler) element;
      dummySampler.setLabel(label.getText());
      dummySampler.setResponseCode(responseCode.getText());
      dummySampler.setSuccessful(success.isSelected());
      try {
        int responseTime = Integer.parseInt(this.responseTime.getText());
        dummySampler.setResponseTime(responseTime);
      } catch (NumberFormatException e) {
        LOG.error("Response time must be Integer", e);
      }
    }
  }

  @Override
  public void configure(TestElement element) {
    super.configure(element);
    if (element instanceof DummySampler) {
      DummySampler dummySampler = (DummySampler) element;
      label.setText(dummySampler.getLabel());
      responseCode.setText(dummySampler.getResponseCode());
      responseTime.setText(dummySampler.getResponseTime().toString());
      success.setSelected(dummySampler.getSuccessful());
    }
  }


}
