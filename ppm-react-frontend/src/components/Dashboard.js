import React, { Component, Fragment } from "react";
import ProjectItem from "./Project/ProjectItem";

class Dashboard extends Component {
  render() {
    return (
        <Fragment>
            <h1>Welcome to Dashboard</h1>
            <ProjectItem />
        </Fragment>
    );
  }
}

export default Dashboard;
