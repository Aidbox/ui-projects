import { describe, expect, test, afterAll, beforeAll } from 'vitest'

beforeAll(async () => {
    console.log("Delete all: ");

    await http({method: "POST",
		url: "/$sql",
		body: ["truncate patient"]});

    await http({method: "POST",
 		url: "/Patient",
		body: {id: "pt-1",
		       name: [{given: ["Paul"], family: "Mask"}]}})

});

afterAll(async () => {
    await http({method: "POST",
		url: "/$sql",
		body: ["truncate patient"]});
});

describe('Patient grid', async () => {
    let resp;
    test("patient grid", async () => {
	resp = await http({method: "GET",
 			   url: "/ui/patients/search",
			   headers: {"X-Aidbox-Dev": "model"}
			  });

	console.log(resp);

	expect(resp).toEqual({
	    params: {},
	    patients: expect.arrayContaining(
		[{id: "pt-1",
		  name: [{family: "Mask", given: ["Paul"]}]}]
	    )});
    });

    test("search should return empty list", async () => {
	resp = await http({method: "GET",
			   url: "/ui/patients/search?query=tahto",
			   headers: {"X-Aidbox-Dev": "model"}});
	expect(resp).toEqual({
	    params: {query: "tahto"},
	    patients: [],
	});

    });

    test("search should return one patient", async () => {
	resp = await http({method: "GET",
			   url: "/ui/patients/search?query=paul",
			   headers: {"X-Aidbox-Dev": "model"}});
	expect(resp).toEqual({
	    params: {query: "paul"},
	    patients: [{id: "pt-1",
			name: [{family: "Mask", given: ["Paul"]}]}],
	});
    })

});

describe('patient form', async () => {
    let resp;

    test("add patient should open empty form", async () => {
	resp = await http({method: "GET",
			   url: "/ui/patients/edit",
			   headers: {"X-Aidbox-Dev": "model"}});

	expect(resp.patient).toBeNull();
    });

    test("edit patient should open filled form", async () => {
	resp = await http({method: "GET",
			   url: "/ui/patients/edit?patient-id=pt-1",
			   headers: {"X-Aidbox-Dev": "model"}});

	expect(resp.patient).toMatchObject({
	    id: "pt-1",
	    name: [{given: ["Paul"], family: "Mask"}]
	});
    });

    test("save patient should return new patient", async () => {
	var formData = new FormData();
	formData.append("id", "pt-1");
	formData.append("given", "Pasha"),
	formData.append("family", "Mask");
	formData.append("gender", "male");

	resp = await http({method: "POST",
			   url: "/ui/patients/save",
			   body: formData,
			   headers: {
			       "content-type": "multipart/form-data",
			       "x-aidbox-dev": "model",
			   }});

	expect(resp).toMatchObject({
	    "saved-patient": {
		id: "pt-1",
		name: [{given: ["Pasha"], family: "Mask"}],
		gender: "male",

	    }});

    });
});

describe("delete patient", async () => {
    let resp;

    test("delete patient", async () => {
	resp = await http({method: "DELETE",
			   url: "/ui/patients/pt-1",
			   headers: {"x-aidbox-dev": "model"}});

	expect(resp.result).toMatchObject({
	    id: "pt-1",
	});
    });

    test("patient grid should not include deleted patient", async () => {
	resp = await http({method: "GET",
			   url: "/ui/patients/search?query=Mask",
			   headers: {"x-aidbox-dev": "model"}});

	expect(resp.patients).toEqual([]);

    });
});
