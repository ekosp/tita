/**
   Copyright 2009 TiTA Project, Vienna University of Technology
   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at
       http://www.apache.org/licenses/LICENSE\-2.0
   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

 */
package at.ac.tuwien.ifs.tita.ui.evaluation.timeconsumer;

import java.util.Date;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.servlet.ServletContext;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;

import org.apache.wicket.RequestCycle;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.datetime.StyleDateConverter;
import org.apache.wicket.datetime.markup.html.form.DateTextField;
import org.apache.wicket.extensions.yui.calendar.DatePicker;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.target.resource.ResourceStreamRequestTarget;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.table.Table;

import at.ac.tuwien.ifs.tita.business.service.time.IEffortService;
import at.ac.tuwien.ifs.tita.business.service.user.IUserService;
import at.ac.tuwien.ifs.tita.entity.Effort;
import at.ac.tuwien.ifs.tita.entity.TiTAUser;
import at.ac.tuwien.ifs.tita.reporting.JasperPdfResource;
import at.ac.tuwien.ifs.tita.ui.BasePage;
import at.ac.tuwien.ifs.tita.ui.login.TitaSession;
import at.ac.tuwien.ifs.tita.ui.models.TableModelTimeConsumerEvaluation;

/**
 * Daily evaluation.
 */
public class DailyViewPage extends BasePage {
    private final Logger log = LoggerFactory.getLogger(DailyViewPage.class);

    @SpringBean(name = "timeEffortService")
    private IEffortService effortService;

    @SpringBean(name = "userService")
    private IUserService userService;

    @SpringBean(name = "dailyViewReport")
    private JasperPdfResource pdfResource;

    private final Date date = new Date();

    private TableModelTimeConsumerEvaluation tableModel;

    public DailyViewPage() {
        initPage();
    }

    /**
     * Inits Page.
     */
    private void initPage() {
        Form<Effort> form = new Form<Effort>("timeConsumerEvaluationForm", new CompoundPropertyModel<Effort>(
                new Effort()));
        add(form);
        form.setOutputMarkupId(true);

        final DateTextField dateTextField = new DateTextField("tedate", new PropertyModel<Date>(this, "date"),
                new StyleDateConverter("S-", true));
        dateTextField.add(new DatePicker());
        form.add(dateTextField);

        final WebMarkupContainer timeeffortContainer = new WebMarkupContainer("timeeffortContainer");
        timeeffortContainer.setOutputMarkupId(true);
        timeeffortContainer.setOutputMarkupPlaceholderTag(true);
        add(timeeffortContainer);

        tableModel = new TableModelTimeConsumerEvaluation(getTimeEffortsDailyView(new Date()));
        Table table = new Table("tetable", tableModel);
        timeeffortContainer.add(table);

        final Button btnShowAsPDF = new Button("btnShowPDF") {
            @Override
            public void onSubmit() {
                try {
                    loadReport();
                    ResourceStreamRequestTarget rsrtarget = new ResourceStreamRequestTarget(pdfResource
                            .getResourceStream());
                    rsrtarget.setFileName(pdfResource.getFilename());
                    RequestCycle.get().setRequestTarget(rsrtarget);
                } catch (JRException e) {
                    // TODO: GUI Exception Handling
                    log.error(e.getMessage());
                } catch (PersistenceException e) {
                    // TODO: GUI Exception Handling
                    log.error(e.getMessage());
                }
            }

            @Override
            public boolean isEnabled() {
                return tableModel.getRowCount() == 0 ? false : true;
            }
        };

        form.add(btnShowAsPDF);

        form.add(new AjaxButton("btnShowEvaluation", form) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form1) {
                tableModel.reload(getTimeEffortsDailyView(dateTextField.getModelObject()));
                target.addComponent(timeeffortContainer);
                target.addComponent(btnShowAsPDF);
            }

            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form1) {
                // TODO Set border red on textfields which are'nt filled
            }
        });

    }

    /**
     * loads report and sets data source.
     * 
     * @throws JRException JasperReports Exception
     * @throws PersistenceException if user cannot be found
     */
    private void loadReport() throws JRException, PersistenceException {
        ServletContext context = ((WebApplication) getApplication()).getServletContext();
        pdfResource.loadReport(context.getRealPath(pdfResource.getDesignFilename()));
        pdfResource.setReportDataSource(new JRTableModelDataSource(tableModel));

        TiTAUser currentUser = userService.getUserByUsername(TitaSession.getSession().getUsername());
        String name = currentUser.getFirstName() + " " + currentUser.getLastName();
        pdfResource.addReportParameter("name", name.replaceAll("\n", ""));

        pdfResource.addReportParameter("month", "");
        pdfResource.addReportParameter("year", "");
    }

    /**
     * Gets time effort data by date.
     * 
     * @param d date of timeeffort entry
     * @return all timeefforts that match the date
     */
    private List<Effort> getTimeEffortsDailyView(Date d) {
        List<Effort> list = null;

        try {
            list = effortService.getEffortsDailyView(d, userService.getUserByUsername(TitaSession.getSession()
                    .getUsername()));
        } catch (PersistenceException e) {
            // TODO: GUI Exception Handling
            log.error(e.getMessage());
        }

        return list;
    }

    /**
     * Returns date for datePicker.
     * 
     * @return the date
     */
    public Date getDate() {
        return date;
    }
}
