package com.nsn.doo.tbox.cmcc.spark.volte;

import com.nsn.datamining.DataminingFactory;
import com.nsn.datamining.XmlDataminingFactory;
import com.nsn.datamining.runtime.Runtime;
import com.nsn.datamining.runtime.RuntimeResourceUtil;
import com.nsn.logger.Logger;
import com.nsn.messages.driver.MessageDriver;
import com.nsn.util.BundleUtil;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import static com.nsn.messages.driver.MessageDriver.EventType.HOURLY;

public class Activator implements BundleActivator {

    private Logger log = Logger.getLogger(this.getClass().getName());
    private static final String ID = "volte";

    private static final String[][] modules = {
        {"volte_mw", "mw小时专题", "/com/nsn/doo/tbox/cmcc/spark/volte/mw.xml"},
        {"volte_sv", "sv小时专题", "/com/nsn/doo/tbox/cmcc/spark/volte/sv.xml"},
        {"bigxdrcmcc", "bigxdr小时专题", "/com/nsn/doo/tbox/cmcc/spark/volte/bigxdrcmcc.xml"},
        {"cmccdrop", "cmccdrop小时专题", "/com/nsn/doo/tbox/cmcc/spark/volte/cmccdrop.xml"},
        {"cmccsrvcc", "cmccsrvcc小时专题", "/com/nsn/doo/tbox/cmcc/spark/volte/cmccsrvcc.xml"},
        {"bigxdrcmcc_render", "bigxdr小时渲染专题", "/com/nsn/doo/tbox/cmcc/spark/volte/bigxdrcmcc_render.xml"},
        {"cmccdrop_render", "cmccdrop小时渲染专题", "/com/nsn/doo/tbox/cmcc/spark/volte/cmccdrop_render.xml"},
        {"cmccsrvcc_render", "cmccsrvcc小时渲染专题", "/com/nsn/doo/tbox/cmcc/spark/volte/cmccsrvcc_render.xml"},
        {"vtv", "vtv小时专题", "/com/nsn/doo/tbox/cmcc/spark/volte/vtv.xml"},
        {"bigxdrdrop_s1mme", "bigxdrdrop_s1mme小时专题", "/com/nsn/doo/tbox/cmcc/spark/volte/bigxdrdrop_s1mme.xml"},
    };

    public void start(final BundleContext context) throws Exception {
        for (final String[] ss : modules) {
            DataminingFactory.register(ss[0], new XmlDataminingFactory(ss[0], ss[1], ss[1], 1, Activator.class.getClassLoader(), Activator.class, ss[2]));
            MessageDriver.Topic topic = new MessageDriver.Topic(ss[0]).type(HOURLY);
            MessageDriver.listen(topic);
        }
        if (Runtime.environment() == Runtime.RuntimeEnvironment.SCHEDULER) {
            com.nsn.datamining.runtime.Runtime runtime = com.nsn.datamining.runtime.Runtime.Registry.runtime("Spark-1");
            for (final String[] ss : modules) {
                runtime.addResource(ss[0], null, context.getBundle().getSymbolicName());
                runtime.addResource(ss[0], RuntimeResourceUtil.URL(ss[2].substring(1), "jar:" + BundleUtil.getBundleUrl(context.getBundle()) + "!" + ss[2]));
            }
        }
        log.info("DO TOOLBOX MODULE[" + ID + "] STARTED!");
    }

    public void stop(BundleContext bundleContext) throws Exception {
        log.info("DO TOOLBOX MODULE[" + ID + "] STOPED!");
    }

}
