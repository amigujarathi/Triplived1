function ghf_field_empty(obj)
{
	if(typeof(obj)=="object")
	{
		if(ghf_Trim(obj.value) == obj.defaultValue)
		{
			obj.value="";
		}
	}
}

function ghf_field_restore(obj)
{
  	if(typeof(obj)=="object")
	{
		if(ghf_Trim(obj.value) == "")
		{
			obj.value= obj.defaultValue;
		}
	}
}


function ghf_LTrim(str)
{
  var whitespace = new String(" \t\n\r");

  var s = new String(str);

  if (whitespace.indexOf(s.charAt(0)) != -1) {
    var j=0, i = s.length;
    while (j < i && whitespace.indexOf(s.charAt(j)) != -1)
    j++;
    s = s.substring(j, i);
  }

  return s;
}

function ghf_RTrim(str)
{
  var whitespace = new String(" \t\n\r");
  var s = new String(str);
  if (whitespace.indexOf(s.charAt(s.length-1)) != -1) {
    var i = s.length - 1;

    while (i >= 0 && whitespace.indexOf(s.charAt(i)) != -1){ i = i-1 ; }
    s = s.substring(0, i+1);
  }
  return s;
}

function ghf_Trim(str)
{
  return ghf_RTrim(ghf_LTrim(str));
}

// Email validation ...
function validateEmail(email) {
   var reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
   if(reg.test(email) == false) {  return false; }
   return true;
}

function hideError_msgs(getId)
{
	var err_msgs_plchldrs = ['chf_error_name','chf_error_email','chf_error_comment','chf_fdbck_succs'];
	
	for(var i=0; i<err_msgs_plchldrs.length; i++)
	{
		if(document.getElementById(err_msgs_plchldrs[i])!=null && document.getElementById(err_msgs_plchldrs[i]).innerHTML=="")
		{
			if(getId!=null ||  getId!='undefined')
			{
				if(err_msgs_plchldrs[i]!=getId)
					document.getElementById(err_msgs_plchldrs[i]).style['display']='none';
				else
					document.getElementById(getId).style['display']='block';
			}
			else
			{
				document.getElementById(err_msgs_plchldrs[i]).style['display']='none';
			}
		}
	} // end of for loop..
}

// Feedback Form validation..
function fdbck_Validate(formObj)
{
	var allElms = formObj.elements;
	
	// Object used for containing errors....	
	var error_counts={
		count_increase:0,
		set:function(key,val)
		{
			this[key] = val;
		},
		get:function(key)
		{
			return this[key];
		}
	};
	
	for(var i=0;i<allElms.length;i++)
	{
		if(allElms[i].name=="full_name")
		{
			if(allElms[i].value=="" || ghf_Trim(allElms[i].value).toLowerCase()=="name")
			{
				error_counts.count_increase = 1;
				error_counts.set(allElms[i].name,allElms[i].value);
			}
			allElms[i].className="";
		} // field-1 validated...
		
		else if(allElms[i].name=="chf_email")
		{
			if(allElms[i].value=="" || ghf_Trim(allElms[i].value).toLowerCase()=="email id")
			{
					error_counts.count_increase = 2;
					error_counts.set(allElms[i].name,allElms[i].value);
			}
			else if(allElms[i].value!="")
			{
					if(!validateEmail(allElms[i].value))
					{
						error_counts.count_increase = 2;
						error_counts.set(allElms[i].name,allElms[i].value);
					}
			}
			allElms[i].className="";
		} // field-2 validated...
		
		else if(allElms[i].name=="your_comment")
		{
			if(allElms[i].value=="" || ghf_Trim(allElms[i].value)=="Your Comments:")
			{
				error_counts.count_increase = 3;
				error_counts.set(allElms[i].name,allElms[i].value);
			}
			allElms[i].className="";
		} // field-3 validated...
		
		// Error messages.. blank..
		document.getElementById("chf_error_name").innerHTML = "";
		document.getElementById("chf_error_email").innerHTML = "";
		document.getElementById("chf_error_comment").innerHTML = "";

	}// end of for loop...
	
	if(error_counts.count_increase > 0)
	{
		for(var objA in error_counts)
		{
			if(typeof(error_counts[objA]) != "function" && typeof(error_counts[objA])=="string" && error_counts[objA]!=null)
			{
				var fld_name = objA;
				if(document.getElementById(fld_name)!=null && document.getElementById(fld_name).className.indexOf("chf_error_flds")==-1)
				{
					document.getElementById(fld_name).className+=" chf_error_flds";
					if(fld_name=="full_name")  document.getElementById("chf_error_name").innerHTML = "* Enter Name";
					else if(fld_name=="chf_email") document.getElementById("chf_error_email").innerHTML = "* Enter a valid E-mail ID";
					else if(fld_name=="your_comment") document.getElementById("chf_error_comment").innerHTML = "* Enter your Comments";
				}
			}
		}
		return false;
	}
	else
	{
		document.getElementById("chf_form_fields").style["display"] = "none";
		document.getElementById("chf_fdbck_succs").innerHTML = "Thanks for your valueable feedback";
		hideError_msgs('chf_fdbck_succs');
		return false; // it needs to be remove when.. form submit in action..
	}
}

function ghf_getNextSibling(startBrother)
{
  endBrother=startBrother.nextSibling;
  while(endBrother.nodeType!=1)
{
  endBrother = endBrother.nextSibling;
}
  return endBrother;

}

function ghf_slideToggle(obj)
{
	var nextElm="";
	if(obj.className.indexOf("ghf_more_tab")!=-1)
	{
		nextElm = document.getElementById("ghf_footer_center");
	}
	else
	{
		nextElm = ghf_getNextSibling(obj);
	}
	var text = document.getElementById("textual");
	var icon = document.getElementById("icons");
	if(nextElm.style.display=="none" && icon.className!=null)
	{
	nextElm.style.display="block";
	text = text.innerHTML="Less";
    icon.className = icon.className.replace("chf_expnd_state","chf_collpse_state");
    }
	else
	{
    nextElm.style.display="none";
	text  = text.innerHTML="More";
    icon.className = icon.className.replace("chf_collpse_state","chf_expnd_state");
	}
}