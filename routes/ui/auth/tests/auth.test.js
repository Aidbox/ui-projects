import { describe, expect, test, beforeAll } from 'vitest'

beforeAll(async () => {
    await http({method: "POST",
		url: "/$sql",
		body: ["truncate patient"]});
});

describe('Patient', async () => {
    test('Create /Patient', async () => {

	let resp;

	resp = await http({method: "POST",
                    url: "/$sql",
                    body: ["truncate patient"]})

	resp = await http({method: "GET",
			   url: "/Patient"});


	resp = await http({method: "POST",
			   url: "/Patient",
			   body: {name: [{given: ["John"]}]}});

	resp = await http({method: "GET",
			   url: "/Patient"});

    })
})
