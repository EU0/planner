import { createDevTools } from "redux-devtools";
import * as React from "react";
import LogMonitor from "redux-devtools-log-monitor";
import DockMonitor from "redux-devtools-dock-monitor";

const DevTools = createDevTools(
  <DockMonitor
    toggleVisibilityKey="ctrl-y"
    changePositionKey="ctrl-q"
    changeMonitorKey="ctrl-m"
    defaultIsVisible={false}
  >
    <LogMonitor theme="tomorrow" />
  </DockMonitor>
);
export default DevTools;
