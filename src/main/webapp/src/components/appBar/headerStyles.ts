import { makeStyles } from "@material-ui/core/styles";

export const useHeaderStyles = makeStyles(theme => ({
  appBar: {
    border: "0",
    borderRadius: "0px 0px 5px 5px",
    padding: "0.625rem 0",
    marginBottom: "20px",
    color: "#d5d5d5",
    width: "100%",
    backgroundColor: "#333",
    boxShadow:
      "0 4px 18px 0px rgba(0, 0, 0, 0.12), 0 7px 10px -5px rgba(0, 0, 0, 0.15)",
    transition: "all 150ms ease 0s",
    zIndex: "unset"
  },
  title: {
    fontSize: 32
  },
  placeholder: {
    height: 64,
    [theme.breakpoints.up("sm")]: {
      height: 70
    }
  },
  toolbar: {
    justifyContent: "space-between"
  },
  left: {
    flex: 1
  },
  leftLinkActive: {
    color: theme.palette.common.white
  },
  right: {
    flex: 1,
    display: "flex",
    justifyContent: "flex-end"
  },
  rightLink: {
    fontSize: 18,
    color: theme.palette.common.white,
    marginLeft: theme.spacing(3)
  },
  linkSecondary: {
    color: theme.palette.secondary.main
  }
}));
