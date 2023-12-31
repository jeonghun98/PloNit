import * as React from "react";
import { styled } from "@mui/material/styles";
import Tabs from "@mui/material/Tabs";
import Tab from "@mui/material/Tab";
import Box from "@mui/material/Box";

interface CustomizedTabsProps {
  tabProps: { [key: string]: React.ReactNode };
}

interface StyledTabsProps {
  children?: React.ReactNode;
  value: number;
  onChange: (event: React.SyntheticEvent, newValue: number) => void;
}

const StyledTabs = styled((props: StyledTabsProps) => (
  <Tabs
    {...props}
    TabIndicatorProps={{ children: <span className="MuiTabs-indicatorSpan" /> }}
  />
))({
  width: "100%",
  "& .MuiTabs-indicator": {
    display: "flex",
    justifyContent: "center",
    backgroundColor: "transparent",
  },
  "& .MuiTabs-indicatorSpan": {
    maxWidth: "100%",
    width: "100%",
    backgroundColor: "#2CD261",
  },
});

interface StyledTabProps {
  label: string;
  tabcount: number;
}

const StyledTab = styled((props: StyledTabProps) => (
  <Tab disableRipple {...props} />
))(({ theme, tabcount }) => ({
  width: `${(100 / tabcount).toFixed(2)}%`,
  textTransform: "none",
  // fontWeight: theme.typography.fontWeightRegular,
  // fontSize: theme.typography.pxToRem(15),
  // 폰트 고정
  fontFamily: "omyu_pretty",
  fontWeight: "bold",
  fontSize: "1.2rem",
  marginRight: theme.spacing(0),
  color: "gray",
  "&.Mui-selected": {
    color: "black",
  },
}));

export default function CustomTab(props: CustomizedTabsProps) {
  const [value, setValue] = React.useState(0);
  const { tabProps } = props;
  const handleChange = (event: React.SyntheticEvent, newValue: number) => {
    setValue(newValue);
  };
  const tabcount = Object.keys(tabProps).length;

  return (
    <Box sx={{ width: "100%" }}>
      <Box sx={{ bgcolor: "white" }}>
        <StyledTabs
          value={value}
          onChange={handleChange}
          aria-label="styled tabs example"
        >
          {Object.keys(tabProps).map((key, index) => (
            <StyledTab key={index} label={key} tabcount={tabcount} />
          ))}
        </StyledTabs>
        <Box sx={{ height: "100%" }}>{Object.values(tabProps)[value]}</Box>
      </Box>
    </Box>
  );
}
